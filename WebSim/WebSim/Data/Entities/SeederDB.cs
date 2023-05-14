using Microsoft.AspNetCore.Identity;
using Microsoft.EntityFrameworkCore;
using WebSim.Contants;
using WebSim.Data.Entities.Identity;

namespace WebSim.Data.Entities
{
    public static class SeederDB
    {
        public static void SeedData(this IApplicationBuilder app)
        {
            using (var scope = app.ApplicationServices
                .GetRequiredService<IServiceScopeFactory>().CreateScope())
            {
                var context = scope.ServiceProvider.GetRequiredService<AppEFContext>();
                var userManager = scope.ServiceProvider.GetRequiredService<UserManager<UserEntity>>();
                var roleManager = scope.ServiceProvider.GetRequiredService<RoleManager<RoleEntity>>();
                context.Database.Migrate();
                if (!context.Categories.Any())
                {
                    var notebook = new CategoryEntity
                    {
                        Name = "Ноутбуки",
                        DateCreated = DateTime.SpecifyKind(DateTime.Now, DateTimeKind.Utc),
                        Priority = 1,
                        Image = "1.jpg",
                        IsDeleted = false,
                        Description = "Для усіх козаків"
                    };
                    context.Categories.Add(notebook);
                    context.SaveChanges();
                    var clothes = new CategoryEntity
                    {
                        Name = "Одяг",
                        DateCreated = DateTime.SpecifyKind(DateTime.Now, DateTimeKind.Utc),
                        Priority = 2,
                        Image = "2.jpg",
                        IsDeleted = false,
                        Description = "Для дівчат"
                    };
                    context.Categories.Add(clothes);
                    context.SaveChanges();
                }
                if (!context.Roles.Any())
                {
                    RoleEntity admin = new RoleEntity()
                    {
                        Name = Roles.Admin
                    };

                    RoleEntity user = new RoleEntity()
                    {
                        Name = Roles.User
                    };
                    var result = roleManager.CreateAsync(admin).Result;
                    result = roleManager.CreateAsync(user).Result;
                }

                if (!context.Users.Any())
                {
                    UserEntity user
                        = new UserEntity()
                        {
                            FirstName = "Іван",
                            LastName = "Марко",
                            Email = "marko@gmail.com",
                            UserName = "marko@gmail.com"
                        };
                    var result = userManager.CreateAsync(user, "123456").Result;
                    if (result.Succeeded)
                    {
                        result = userManager.AddToRoleAsync(user, Roles.Admin).Result;
                    }
                }
            }
        }
    }
}
