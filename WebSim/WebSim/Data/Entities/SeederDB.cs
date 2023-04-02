using Microsoft.EntityFrameworkCore;

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
            }
        }
    }
}
