package model;

public class ValidateInput {
	
    public boolean checkUsername(String username){
    	return username.matches("[_\\da-z]{3,}") && username.matches(".*[a-z]{3}.*");
    }
    
    public boolean checkPass(String pass) {
    	return pass.matches("[a-zA-Z0-9]{8,}")&& pass.matches(".*\\d.*");
    }

    public boolean checkEmail(String email) {
    	return email.matches("[a-z]{3,}[\\d]*@.+[.]com");
    }
    
    public boolean checkPhone(String phone) {
    	return phone.matches("06[789]\\d{7}") || phone.matches("06[789] \\d\\d \\d\\d \\d\\d\\d") || phone.matches("[+]\\d{1,3} \\d{2} \\d{2} \\d{2} \\d{3}");
    	
    }
    
    public boolean checksalary(String salary) {
    	return true;
    	
    }
}
