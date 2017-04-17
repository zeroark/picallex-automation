package zeroark.picallex.entities;

public class Entry {
	public String Phone;
	public String Name;
	public String Notes;
	
	public String getPhone() {
		return Phone;
	}
	public void setPhone(String phone) {
		Phone = phone;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getNotes() {
		return Notes;
	}
	public void setNotes(String notes) {
		Notes = notes;
	}
	
	@Override
	public String toString()
	{
		return Phone + "," + Name + ",\n";
	}
}
