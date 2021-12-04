package io.github.ailtonbsj.internshipmanager;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

@SuppressWarnings("serial")
public class UpCaseField extends PlainDocument {

    public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {  
        if (str == null) {  
            return;  
        }  
  
        super.insertString(offset, str.toUpperCase(), attr);  
    }
	
}