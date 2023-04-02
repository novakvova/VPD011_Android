using Microsoft.EntityFrameworkCore;
using WebSim.Data.Entities;

namespace WebSim.Data
{
    public class AppEFContext : DbContext
    {
        public AppEFContext(DbContextOptions<AppEFContext> options)
            :base(options)
        {
            
        }

        public DbSet<CategoryEntity> Categories { get; set; }
    }
}
