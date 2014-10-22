package chococraft.common.carrier;

import java.util.ArrayList;

public class CarrierNodeRepository
{
	// todo: find next free id
	// todo: calculate id from x, y and z?
	
	private static CarrierNodeRepository instance;
	private ArrayList<CarrierNode> carrierNodes;
	
	private CarrierNodeRepository()
	{
		this.carrierNodes = new ArrayList<CarrierNode>();
	}
	
	public static CarrierNodeRepository Instance()
	{
		if(null == CarrierNodeRepository.instance)
		{
			CarrierNodeRepository.instance = new CarrierNodeRepository();
		}
		return CarrierNodeRepository.instance;
	}
	
	public CarrierNode getCarrierNode(int nodeId)
	{
		for (CarrierNode node : this.carrierNodes)
		{
			if(node.nodeId == nodeId)
			{
				return node;
			}
		}
		return null;
	}
}
