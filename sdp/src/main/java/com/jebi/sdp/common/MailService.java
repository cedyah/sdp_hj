package com.jebi.sdp.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

import com.jebi.sdp.model.EmailVO;

/**
 * 메일 발송
 */
@Component
public class MailService {
	@Autowired
    private MailSender mailSender;

    public void sendMail(EmailVO mailVO) throws Exception {
    	SimpleMailMessage message = new SimpleMailMessage();
    	
    	message.setFrom(mailVO.getFrom());
    	
    	if(!mailVO.getTo().equals("")) {
    		message.setTo(mailVO.getTo());
    	} else {
    		//ArrayList 를 StringArray 로 변환하여 message 객체 셋팅
    		String[] to_li =(String[]) (mailVO.getLi_to()).toArray(new String[(mailVO.getLi_to()).size()]); 
    		message.setTo(to_li);
    	}
//    	message.setTo("hjg2223@naver.com");
//    	message.setTo("byounghwa@kangnam.co.kr");
    	message.setSubject(mailVO.getSubject());
    	message.setText(mailVO.getContents());
    	mailSender.send(message);
    }
}
