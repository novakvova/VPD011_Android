using AutoMapper;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using WebSim.Data;
using WebSim.Models;

namespace WebSim.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class CategoriesController : ControllerBase
    {
        private readonly IMapper _mapper;
        private readonly AppEFContext _appEFContext;

        public CategoriesController(IMapper mapper, AppEFContext appEFContext)
        {
            _mapper = mapper;
            _appEFContext = appEFContext;
        }

        [HttpGet("list")]
        public async Task<IActionResult> Get()
        {
            var model = await _appEFContext.Categories
                .Where(x => x.IsDeleted == false)
                .OrderBy(x => x.Priority)
                .Select(x => _mapper.Map<CategoryItemViewModel>(x))
                .ToListAsync();
            return Ok(model);
        }
    }
}
