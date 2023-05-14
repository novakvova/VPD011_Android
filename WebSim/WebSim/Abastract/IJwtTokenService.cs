using WebSim.Data.Entities.Identity;

namespace WebSim.Abastract
{
    public interface IJwtTokenService
    {
        Task<string> CreateToken(UserEntity user);
    }
}
