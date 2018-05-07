package model;

import org.siso.spacefom.frame.SpaceTimeCoordinateState;
import model.SpaceTimeCoordinateStateCoder;
import skf.coder.HLAunicodeStringCoder;
import skf.model.object.annotations.Attribute;
import skf.model.object.annotations.ObjectClass;
import org.apache.commons.math3.complex.Quaternion;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

@ObjectClass(name = "PhysicalEntity.Astronaut_HM")
public class Astronaut_HM {
	
	@Attribute(name = "name", coder = HLAunicodeStringCoder.class)
	private String name = null;
	
	@Attribute(name = "parent_reference_frame", coder = HLAunicodeStringCoder.class)
	private String parent_name = null;
	
	@Attribute(name = "state", coder = SpaceTimeCoordinateStateCoder.class)
	private SpaceTimeCoordinateState state = null;
	
	private double startActionTime = 0;
	
	private SpaceWalkStates spcWlkState = SpaceWalkStates.IDLE;
	
	public enum SpaceWalkStates { EXITING_STATION, CIRCLING_STATION, ENTERING_STATION, IDLE }
	
	public Astronaut_HM(){}

	public Astronaut_HM(String name, String parent_name, Position position) {
		this.name = name;
		this.parent_name = parent_name;
		this.state = new SpaceTimeCoordinateState();
		Quaternion rotation = new Quaternion(0, 0, 0, 1);
		this.state.getRotationState().setAttitudeQuaternion(rotation);
		this.setPosition(position);
	}

	public void setPosition(Position position) {
		Vector3D vector3d = new Vector3D(position.getX(), position.getY(), position.getZ());
		this.state.getTranslationalState().setPosition(vector3d);
	}

	public Position getPosition() {
		Vector3D vector3d = state.getTranslationalState().getPosition();
		return new Position(vector3d.getX(), vector3d.getY(), vector3d.getZ());
	}
	
	public void updatePosition(double time)
	{
		double r = 10;
		double speed = 1;
		double phi = speed / (2*Math.PI*r);
		double timeDelta = (time - this.startActionTime) / 1000000;
		
		Position pos = this.getPosition();
		double x = pos.getX(), y = pos.getY(), z = pos.getZ();
		
		switch (this.spcWlkState)
		{
		case EXITING_STATION:
			if (x < r)
				x = speed*timeDelta;
			else
			{
				this.spcWlkState = SpaceWalkStates.CIRCLING_STATION;
				this.startActionTime = time;
			}
			break;
		case CIRCLING_STATION:
			if (timeDelta < 1/phi)
			{
				x = r*Math.cos(phi*timeDelta*2*Math.PI);
				y = r*Math.sin(phi*timeDelta*2*Math.PI);
			}
			else
			{
				this.spcWlkState = SpaceWalkStates.ENTERING_STATION;
				this.startActionTime = time;
			}
			break;
		case ENTERING_STATION:
			if (x > 0)
				x = r - timeDelta*speed;
			else
				this.spcWlkState = SpaceWalkStates.IDLE;
			break;
		case IDLE:
			break;
		default:
			break;
		}
		setPosition(new Position(x, y, z));
	}
	
	public void startSpaceWalk(double time)
	{
		this.startActionTime = time;
		this.spcWlkState = SpaceWalkStates.EXITING_STATION;
	}
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the parent_name
	 */
	public String getParent_name() {
		return parent_name;
	}

	/**
	 * @param parent_name the parent_name to set
	 */
	public void setParent_name(String parent_name) {
		this.parent_name = parent_name;
	}
	
	public SpaceTimeCoordinateState getState() {
		return state;
	}

	public void setState(SpaceTimeCoordinateState state) {
		this.state = state;
	}

}
