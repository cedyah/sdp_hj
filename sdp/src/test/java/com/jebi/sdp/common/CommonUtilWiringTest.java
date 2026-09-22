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
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.jebi.sdp.model.FileVO;

/**
 * 컨트롤러처럼 CommonUtil 을 상속한 빈에 MailService/FileService 가 주입되어
 * 기존 sendMail/uploadFile/deleteFile 호출이 그대로 동작하는지 확인한다.
 */
public class CommonUtilWiringTest {

	/** 컨트롤러 역할을 하는 CommonUtil 하위 클래스 */
	public static class FakeController extends CommonUtil {
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
		SimpleMailMessage preConfigured = new SimpleMailMessage();
		preConfigured.setFrom("noreply@test");
		preConfigured.setTo("admin@test");
		preConfigured.setSubject("pre");

		ctx = new AnnotationConfigApplicationContext();
		ctx.getBeanFactory().registerSingleton("mailSender", mailSender);
		ctx.getBeanFactory().registerSingleton("preConfiguredMessage", preConfigured);
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
	public void sendMailThroughSubclass() throws Exception {
		controller.sendMail("a@test", new String[] { "b@test" }, "subj", "body");
		controller.sendPreConfiguredMail("hello");

		assertEquals(2, mailSender.sent.size());
		assertEquals("a@test", mailSender.sent.get(0).getFrom());
		assertArrayEquals(new String[] { "b@test" }, mailSender.sent.get(0).getTo());
		assertEquals("subj", mailSender.sent.get(0).getSubject());
		assertEquals("body", mailSender.sent.get(0).getText());
		assertEquals("pre", mailSender.sent.get(1).getSubject());
		assertEquals("hello", mailSender.sent.get(1).getText());
	}

	@Test
	public void uploadAndDeleteThroughSubclass() throws Exception {
		File dir = File.createTempFile("cuwire", "");
		dir.delete();
		bindEmptyRequest();
		FileVO fileVO = new FileVO();
		fileVO.setFile_path(dir.getAbsolutePath() + "/");
		fileVO.setFile_nm("a.txt");

		assertTrue(controller.uploadFile(new BytesMultipartFile("abc".getBytes()), fileVO));
		File saved = new File(dir, "a.txt");
		assertTrue(saved.exists());
		assertEquals(3, saved.length());

		assertFalse(controller.uploadFile(new BytesMultipartFile(new byte[0]), fileVO));

		controller.deleteFile(fileVO);
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
