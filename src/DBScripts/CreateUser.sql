-- Choose a string password
CREATE USER webUser WITH PASSWORD = 'Password@12345';

-- Grant permissions
GRANT SELECT,INSERT,UPDATE,DELETE ON OBJECT::dbo.Genre TO webUser;
GRANT SELECT,INSERT,UPDATE,DELETE ON OBJECT::dbo.Movie TO webUser;
