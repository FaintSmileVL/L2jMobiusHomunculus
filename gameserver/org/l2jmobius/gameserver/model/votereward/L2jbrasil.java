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
public class L2jbrasil extends VoteSystem
{
	public L2jbrasil(int votesDiff, boolean allowReport, int boxes, Map<Integer, Integer> rewards, int checkMins)
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
			URLConnection con = new URL(Config.L2JBRASIL_SERVER_LINK).openConnection();
			con.addRequestProperty("User-Agent", "Mozilla/5.0");
			isr = new InputStreamReader(con.getInputStream());
			br = new BufferedReader(isr);
			
			String line;
			while ((line = br.readLine()) != null)
			{
				if (line.contains("<b>Entradas(Total):</b"))
				{
					String votesResult = String.valueOf(line.split(">")[2].replace("<br /", ""));
					int votes = Integer.valueOf(votesResult.replace(" ", ""));
					return votes;
				}
			}
			
			br.close();
			isr.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			LOGGER.warning("VoteSystem: Error while getting server vote count from " + getSiteName() + ".");
		}
		
		return -1;
	}
	
	@Override
	public String getSiteName()
	{
		return "L2JBrasil";
	}
}