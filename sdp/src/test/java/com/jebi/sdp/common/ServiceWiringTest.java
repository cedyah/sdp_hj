package com.jebi.sdp.common;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.jebi.sdp.model.EmailVO;
import com.jebi.sdp.model.FileVO;

/**
 * 컨트롤러처럼 MailService/FileService 를 @Autowired 로 주입받은 빈에서
 * sendMail/uploadFile/deleteFile 호출이 그대로 동작하는지 확인한다.
 */
public class ServiceWiringTest {

	/** 컨트롤러 역할을 하는 빈 (Sdpe0010Controller, Sdpy0010Controller 와 같은 주입 방식) */
	public static class FakeController {
		@Autowired
		MailService mailService;

		@Autowired
		FileService fileService;
	}

	static class RecordingMailSender implements MailSender {
		List<SimpleMailMessage> sent = new ArrayList<SimpleMailMessage>();

		public void send(SimpleMailMessage message) {
			sent.add(message);
		}

		public void send(SimpleMailMessage[] messages) {
			for (SimpleMailMessage m : messages) {
				sent.add(m);
			}
		}
	}

	private AnnotationConfigApplicationContext ctx;
	private RecordingMailSender mailSender;
	private FakeController controller;

	@Before
	public void setUp() {
		mailSender = new RecordingMailSender();

		ctx = new AnnotationConfigApplicationContext();
		ctx.getBeanFactory().registerSingleton("mailSender", mailSender);
		ctx.register(MailService.class, FileService.class, FakeController.class);
		ctx.refresh();
		controller = ctx.getBean(FakeController.class);
	}

	@After
	public void tearDown() {
		ctx.close();
		RequestContextHolder.resetRequestAttributes();
	}

	/** FileVO(CustomerVO) 생성자가 현재 요청의 세션을 읽으므로 빈 세션을 가진 가짜 요청을 바인딩한다. */
	private static void bindEmptyRequest() {
		final HttpSession session = stub(HttpSession.class, null);
		HttpServletRequest request = stub(HttpServletRequest.class, session);
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
	}

	@SuppressWarnings("unchecked")
	private static <T> T stub(Class<T> type, final Object sessionToReturn) {
		return (T) Proxy.newProxyInstance(type.getClassLoader(), new Class<?>[] { type }, new InvocationHandler() {
			public Object invoke(Object proxy, Method method, Object[] args) {
				if (method.getName().equals("getSession")) {
					return sessionToReturn;
				}
				return null;
			}
		});
	}

	@Test
	public void sendMail() throws Exception {
		EmailVO single = new EmailVO();
		single.setFrom("a@test");
		single.setTo("b@test");
		single.setSubject("subj");
		single.setContents("body");
		controller.mailService.sendMail(single);

		// to 가 빈 문자열이면 li_to 목록으로 보낸다
		EmailVO multi = new EmailVO();
		multi.setFrom("a@test");
		multi.setTo("");
		multi.setLi_to(Arrays.asList("c@test", "d@test"));
		multi.setSubject("subj2");
		multi.setContents("body2");
		controller.mailService.sendMail(multi);

		assertEquals(2, mailSender.sent.size());
		assertEquals("a@test", mailSender.sent.get(0).getFrom());
		assertArrayEquals(new String[] { "b@test" }, mailSender.sent.get(0).getTo());
		assertEquals("subj", mailSender.sent.get(0).getSubject());
		assertEquals("body", mailSender.sent.get(0).getText());
		assertArrayEquals(new String[] { "c@test", "d@test" }, mailSender.sent.get(1).getTo());
		assertEquals("body2", mailSender.sent.get(1).getText());
	}

	@Test
	public void uploadAndDelete() throws Exception {
		File dir = File.createTempFile("cuwire", "");
		dir.delete();
		bindEmptyRequest();
		FileVO fileVO = new FileVO();
		fileVO.setFile_path(dir.getAbsolutePath() + "/");
		fileVO.setFile_nm("a.txt");

		assertTrue(controller.fileService.uploadFile(new BytesMultipartFile("abc".getBytes()), fileVO));
		File saved = new File(dir, "a.txt");
		assertTrue(saved.exists());
		assertEquals(3, saved.length());

		assertFalse(controller.fileService.uploadFile(new BytesMultipartFile(new byte[0]), fileVO));

		controller.fileService.deleteFile(fileVO);
		assertFalse(saved.exists());
	}

	static class BytesMultipartFile implements MultipartFile {
		private final byte[] bytes;

		BytesMultipartFile(byte[] bytes) {
			this.bytes = bytes;
		}

		public String getName() {
			return "file";
		}

		public String getOriginalFilename() {
			return "a.txt";
		}

		public String getContentType() {
			return "text/plain";
		}

		public boolean isEmpty() {
			return bytes.length == 0;
		}

		public long getSize() {
			return bytes.length;
		}

		public byte[] getBytes() {
			return bytes;
		}

		public InputStream getInputStream() {
			return new ByteArrayInputStream(bytes);
		}

		public void transferTo(File dest) throws java.io.IOException {
			FileOutputStream out = new FileOutputStream(dest);
			try {
				out.write(bytes);
			} finally {
				out.close();
			}
		}
	}
}
