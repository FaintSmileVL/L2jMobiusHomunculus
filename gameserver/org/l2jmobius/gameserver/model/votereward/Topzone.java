package org.l2jmobius.gameserver.model.votereward;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

import org.l2jmobius.Config;

/**
 * @author Anarchy
 */
public class Topzone extends VoteSystem
{
	public Topzone(int votesDiff, boolean allowReport, int boxes, Map<Integer, Integer> rewards, int checkMins)
	{
		super(votesDiff, allowReport, boxes, rewards, checkMins);
	}
	
	@Override
	public void run()
	{
		reward();
	}
	
	@Override
	public int getVotes()
	{
		InputStreamReader isr = null;
		BufferedReader br = null;
		
		try
		{
			final URLConnection con = new URL(Config.TOPZONE_SERVER_LINK).openConnection();
			con.addRequestProperty("User-Agent", "L2TopZone");
			isr = new InputStreamReader(con.getInputStream());
			br = new BufferedReader(isr);
			
			String line;
			while ((line = br.readLine()) != null)
			{
				return Integer.parseInt(line.split("fa-thumbs-up\"></i>")[1].split("</span>")[0]);
			}
			
			br.close();
			isr.close();
		}
		catch (Exception e)
		{
			LOGGER.warning("VoteSystem: Error while getting server vote count from " + getSiteName() + ".");
		}
		
		return -1;
	}
	
	@Override
	public String getSiteName()
	{
		return "Topzone.com";
	}
}
