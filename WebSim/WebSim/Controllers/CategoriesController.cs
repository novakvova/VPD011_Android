using AutoMapper;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Identity;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using WebSim.Data;
using WebSim.Data.Entities;
using WebSim.Data.Entities.Identity;
using WebSim.Helpers;
using WebSim.Models;

namespace WebSim.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    [Authorize]
    public class CategoriesController : ControllerBase
    {
        private readonly IMapper _mapper;
        private readonly AppEFContext _appEFContext;
        private readonly UserManager<UserEntity> _userManager;
        public CategoriesController(IMapper mapper, AppEFContext appEFContext, 
            UserManager<UserEntity> userManager)
        {
            _mapper = mapper;
            _appEFContext = appEFContext;
            _userManager = userManager;
        }

        [HttpGet("list")]
        public async Task<IActionResult> Get()
        {
            string userName = User.Claims.First().Value;
            var user = await _userManager.FindByEmailAsync(userName);
            Thread.Sleep(1000);
            var model = await _appEFContext.Categories
                .Where(x => x.IsDeleted == false)
                .Where(x => x.UserId == user.Id)
                .OrderBy(x => x.Priority)
                .Select(x => _mapper.Map<CategoryItemViewModel>(x))
                .ToListAsync();
            return Ok(model);
        }

        [HttpGet("{id}")]
        public async Task<IActionResult> GetById(int id)
        {
            string userName = User.Claims.First().Value;
            var user = await _userManager.FindByEmailAsync(userName);
            var model = await _appEFContext.Categories
                .Where(x => x.UserId == user.Id)
                .Where(x => x.IsDeleted == false && x.Id == id)
                .Select(x => _mapper.Map<CategoryItemViewModel>(x))
                .ToListAsync();
            if (model.Count == 0)
                return NotFound();

            return Ok(model[0]);
        }

        [HttpPost("create")]
        public async Task<IActionResult> Create([FromBody] CategoryCreateItemVM model)
        {
            string userName = User.Claims.First().Value;
            var user = await _userManager.FindByEmailAsync(userName);
            try
            {
                var category = _mapper.Map<CategoryEntity>(model);
                category.Image = ImageWorker.SaveImage(model.ImageBase64);
                category.UserId = user.Id;
                await _appEFContext.Categories.AddAsync(category);
                await _appEFContext.SaveChangesAsync();
                return Ok(_mapper.Map<CategoryItemViewModel>(category));

            }
            catch (Exception ex)
            {
                return BadRequest(new {error= ex.Message});
            }
        }
        [HttpPut("update")]
        public async Task<IActionResult> Update([FromBody] CategoryUpdateeItemVM model)
        {
            string userName = User.Claims.First().Value;
            var user = await _userManager.FindByEmailAsync(userName);
            var cat = await _appEFContext.Categories
                    .Where(x => x.UserId == user.Id)
                    .SingleOrDefaultAsync(x => x.Id == model.Id);
            if (cat == null)
                return NotFound();
            cat.Priority = model.Priority;
            cat.Name = model.Name;
            cat.Description = model.Description;
            if (!string.IsNullOrEmpty(model.ImageBase64))
            {
                ImageWorker.RemoveImage(cat.Image);
                cat.Image = ImageWorker.SaveImage(model.ImageBase64);
            }
            _appEFContext.Update(cat);
            await _appEFContext.SaveChangesAsync();
            return Ok();
        }
        [HttpDelete("{id}")]
        public async Task<IActionResult> Delete(int id)
        {
            string userName = User.Claims.First().Value;
            var user = await _userManager.FindByEmailAsync(userName);
            var cat = await _appEFContext.Categories
                   .Where(x => x.UserId == user.Id)
                   .SingleOrDefaultAsync(x => x.Id == id);
            if (cat == null)
                return NotFound();
            cat.IsDeleted = true;
            _appEFContext.SaveChanges();
            return Ok();    
        }

    }
}
