package database;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author Mobius
 */
public class DatabaseBackup
{
	public static void performBackup(int backUpDays, String backUpPath, String mySqlBinPath, String dbLogin, String dbPassword, String dbUrl, String serverStatus)
	{
		// Delete old files.
		if (backUpDays > 0)
		{
			final long cut = LocalDateTime.now().minusDays(backUpDays).toEpochSecond(ZoneOffset.UTC);
			final Path path = Paths.get(backUpPath);
			try
			{
				Files.list(path).filter(n ->
				{
					try
					{
						return Files.getLastModifiedTime(n).to(TimeUnit.SECONDS) < cut;
					}
					catch (Exception ex)
					{
						return false;
					}
				}).forEach(n ->
				{
					try
					{
						Files.delete(n);
					}
					catch (Exception ex)
					{
						// Ignore.
					}
				});
			}
			catch (Exception e)
			{
				// Ignore.
			}
		}
		
		// Dump to file.
		final String mysqldumpPath = System.getProperty("os.name").toLowerCase().contains("win") ? mySqlBinPath : "";
		try
		{
			final Process process = Runtime.getRuntime().exec(mysqldumpPath + "mysqldump -u " + dbLogin + (dbPassword.trim().isEmpty() ? "" : " -p" + dbPassword) + " " + dbUrl.replace("jdbc:mariadb://", "").replaceAll(".*\\/|\\?.*", "") + " -r " + backUpPath + (serverStatus) + new SimpleDateFormat("_yyyy_MM_dd_HH_mm'.sql'").format(new Date()));
			process.waitFor();
		}
		catch (Exception e)
		{
			// Ignore.
		}
	}
}
