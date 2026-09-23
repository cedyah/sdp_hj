# 리팩토링 기록 (`refactor/commonutil` 브랜치)

2026-09-22 ~ 09-23 에 진행한 정리 작업을 기록한다. 동작을 바꾸지 않는 것을 원칙으로 했고,
달라지는 부분은 각 절에 따로 적었다.

| 작업 | 결과 |
|---|---|
| [공통 유틸 분리](#1-공통-유틸-comjebisdpcommon) | `CommonUtil` 1,668줄 삭제, 실제 쓰는 메서드 7개만 유지 |
| [빌드 수정](#6-빌드와-테스트) | `mvn test` / `mvn package` 가 처음으로 통과 |
| [죽은 JavaScript 삭제](#9-죽은-javascript-삭제) | 1,987줄 삭제 |
| [샘플의뢰 중복 제거](#10-샘플의뢰-화면-중복-제거-sdph0050sdph0052) | 두 컨트롤러 1,239줄 → 989줄 |
| [제조의뢰 서비스 분리](#11-제조의뢰-화면-서비스-분리-sdpa0040) | 컨트롤러 644줄 → 336줄 + 서비스 300줄 |
| [접속 정보 분리](#7-접속-정보-분리) | DB·메일 비밀번호를 `sdp.properties`(커밋 제외)로 이동 |

**아직 Tomcat 에서 실제로 띄워 확인하지 않았다.** [배포 전 수동 확인 항목](#배포-전-수동-확인-항목) 참고.

## 1. 공통 유틸 (`com.jebi.sdp.common`)

| 이전 | 이후 |
|---|---|
| 컨트롤러 29개가 `extends CommonUtil` 로 유틸 메서드를 상속 | 상속 없음. static 메서드는 `import static`, 메일·파일은 서비스 주입 |
| `CommonUtil` 한 클래스에 문자열·날짜·암호화·메일·파일·외부 API 호출 약 80개 메서드 | 용도별 클래스 5개, 실제로 호출되는 메서드 7개만 유지 |
| 테스트 없음 | `src/test` 에 JUnit 테스트 5개 |

`CommonUtil.java`, `CryptoUtil.java` 는 삭제되었다.

## 2. 공통 유틸 클래스 구성

| 클래스 | 종류 | 메서드 | 사용처 |
|---|---|---|---|
| `StringUtil` | static | `nvl(String)`, `getReplaceString(String)` | `nvl`: Sdpf0020 |
| `FormatUtil` | static | `getExpDateString(String)`, `getExpNumString(String)` | 날짜·금액 검색 조건이 있는 컨트롤러 대부분 |
| `DateUtil` | static | `getYYYY()` | Sdpb0010, Sdpd0010 |
| `MailService` | `@Component` | `sendMail(EmailVO)` | Sdpe0010, Sdpe0020 |
| `FileService` | `@Component` | `uploadFile(MultipartFile, FileVO)`, `deleteFile(FileVO)` | Sdpy0010 |

같은 패키지의 나머지 클래스는 이번 작업과 관계없다.

- `SessionInterceptor`: 로그인 세션 검사 인터셉터
- `SessionFieldBindingGuard`: 로그인 상태에서 `cust_num`, `workplace` 등 세션 소유 필드를 요청 파라미터로 덮어쓰지 못하게 막음
- `OracleUsTypeHandler`: iBatis 문자셋 변환 타입 핸들러

### 동작 참고 (기존 동작 그대로 유지)

- `nvl`: `null` 또는 문자열 `"null"`이면 `""`를 반환한다. 그 외에는 앞뒤 공백을 제거하고, `"` `'` `<` `>`를 전각 문자나 HTML 엔티티로 바꾼다.
- `getExpDateString`: 앞뒤 공백을 제거하고 `-` `,` `.` `/`를 지운다(`" 2025-01/02 "` → `"20250102"`). 인자가 `null`이면 `""`를 반환한다.
- `getExpNumString`: 앞뒤 공백을 제거하고 `,`를 지운다. 이것도 `null`이면 `""`를 반환한다.
- `sendMail(EmailVO)`: `to`가 빈 문자열이면 `li_to` 목록으로 보낸다. `to`가 `null`이면 NPE가 발생한다.
- `uploadFile`: `file_path` 폴더가 없으면 만들고, `file_path + file_nm`에 저장한다. 빈 파일이면 저장하지 않고 `false`를 반환한다.

## 3. 컨트롤러에서 공통 유틸을 쓰는 방법

```java
import static com.jebi.sdp.common.FormatUtil.getExpDateString;

@Controller
public class SdpX0010Controller {
	@Autowired
	private MailService mailService;   // 메일이 필요할 때만

	@Autowired
	private FileService fileService;   // 첨부파일이 필요할 때만

	@Autowired
	private CmmnDao dao;

	...
	map.put("ARG_FRDT", getExpDateString(vo.getSearchDate_from()));
	mailService.sendMail(emailVO);
	fileService.uploadFile(mFile, fileVO);
}
```

- 새 컨트롤러에 `extends` 를 쓰지 않는다. 필요한 static 메서드만 import 한다.
- `MailService`는 `servlet-context.xml`의 `mailSender` 빈을 주입받는다.
- 두 서비스 모두 `component-scan base-package="com.jebi.sdp"`로 등록된다.
- 필요한 유틸이 없으면 용도에 맞는 클래스에 추가한다. 삭제된 메서드는 아래 4절에서 되살릴 수 있다.

## 4. 삭제한 공통 유틸 메서드

호출하는 곳(Java, JSP, iBatis XML)이 없어서 삭제했다. 필요하면 git 이력에서 가져온다.

- `CommonUtil`: 위임 메서드 전부와 `urlParsing`(서울시 OpenAPI 테스트 코드)
- `CryptoUtil`: `encrypt`/`decrypt`(AES), hex 변환, `randomKey`
- `StringUtil`: `nvl2`, `nvlDate`, `nvlNum`, `replace*`, `align*`, `split`, `join` 등 26개
- `FormatUtil`: `getComma`, `getFormatBizId`, `getFormatJuminNo`, `addZero`
- `DateUtil`: `getFormatDate*`, `getMM`/`getDD`/`getYYYYMMDD` 등, `compareTo*` 16개
- `MailService`: `sendMail(String, String[], String, String)`, `sendPreConfiguredMail`
- `FileService`: `makeDir`, `getFileAlias`

삭제 직전 버전(모든 메서드가 새 클래스로 분리된 상태)은 커밋 `e6a1788`에 있다.

```bash
git show e6a1788:sdp/src/main/java/com/jebi/sdp/common/CryptoUtil.java
```

> `CryptoUtil` 을 되살릴 경우 키 값(`KEY`)과 `getBytes()` 기본 charset 동작을 바꾸지 말 것. 바꾸면 기존 암호문을 복호화할 수 없다. 원래 동작은 `e6a1788` 의 `CommonUtilTest#crypto` 에 고정되어 있다.

## 5. 커밋 이력

| 커밋 | 내용 |
|---|---|
| `245e18a` | 리팩토링 전 `CommonUtil` 동작을 고정하는 특성 테스트 추가 |
| `e6a1788` | 로직을 용도별 클래스로 이동, `CommonUtil`은 위임 클래스로 축소 (컨트롤러 변경 없음) |
| `af661be` | 컨트롤러 29개에서 상속 제거, static import와 서비스 주입으로 교체 |
| `314fe10` | 호출되지 않는 메서드와 `CommonUtil`, `CryptoUtil` 삭제 |
| `61c91b7` | 이 문서 추가 |
| `c94e09d` | `mvn` 빌드 실패 수정 (ojdbc14 저장소, javafx import, 중복 의존성) |
| `3396291` | 사용하지 않는 `preConfiguredMessage` 빈 제거 |
| `4015d87` | `getExpDateString`·`getExpNumString` 의 `null` 인자 처리 |
| `4f69f62` | 사용하지 않는 JavaScript 파일 삭제 |
| `aabfcc9` | `Sdph0050`·`Sdph0052` 중복 제거와 디버그 출력 정리 |
| `4afe2e8` | `Sdpa0040` 의 DB 처리를 `ProdReqService` 로 분리 |

## 6. 빌드와 테스트

```bash
cd sdp
mvn test       # 컴파일 + 테스트
mvn package    # target/sdp-1.0.1.war 생성
```

`ojdbc14`는 공개 저장소에서 받을 수 없어 jar를 저장소에 함께 보관한다. `sdp/lib/`가 Maven 저장소 구조(`groupId/artifactId/version`)를 그대로 따르고, `pom.xml`이 이 폴더를 `file://` 저장소로 참조한다. 따라서 각자 `~/.m2`에 수동 설치할 필요가 없다.

> 예전에는 `ojdbc14`를 HTTP 저장소에서 받도록 되어 있어(최근 Maven이 차단) `ReportController`의 javafx import와 함께 `mvn` 빌드가 실패했다. 커밋 `c94e09d`에서 고쳤다.

테스트 내용은 다음과 같다.

- `UtilTest`: `nvl`, `getExpDateString`, `getExpNumString`, `getYYYY`의 기존 출력을 고정한다.
- `ServiceWiringTest`: Spring 컨텍스트에서 `@Autowired`로 받은 `MailService`와 `FileService`가 실제로 메일을 만들고 파일을 저장·삭제하는지 확인한다.
- `SampleRequestParamsTest`: 샘플의뢰 헤더 프로시저 파라미터 35개와, 작성·수정의 날짜 처리 차이를 고정한다.

DB 가 필요한 화면 동작은 테스트로 덮지 못한다. 컨트롤러를 고칠 때는 프로시저 이름과
파라미터(키·값)를 원본과 대조하는 방식으로 확인했다(9·10절).

### 로그 설정

`src/main/resources/log4j.xml` 하나만 쓴다(`bak1_log4j.xml`, `bak2_log4j2.xml` 은 참조하는 곳이 없어 삭제했다).

- 로그 파일: `${catalina.base}/logs/sdp-error.log`. 예전에는 Windows 절대경로(`C:\logs\error\error.log`)여서
  다른 OS 에서는 작업 폴더에 그 이름의 파일이 생겼다. **운영 서버의 로그 위치가 Tomcat 의 logs 폴더로 바뀐다.**
- 콘솔 패턴의 `%t%gt;` 는 `%g` 가 없는 변환문자여서 기동할 때마다 `log4j:ERROR` 가 뜨고
  스레드 이름이 빠졌다. `%t&gt;`(= `%t>`)로 고쳤다.
- root 로거가 `warn` 이므로 컨트롤러의 `logger.debug` 는 남지 않는다. `logger.error` 는 콘솔과 파일에 모두 남는다.

### 로컬에서 띄워 보기

Tomcat 을 설치하지 않고 Maven 플러그인으로 띄울 수 있다.

```bash
cd sdp
mvn org.apache.tomcat.maven:tomcat7-maven-plugin:2.2:run -Dmaven.tomcat.port=8080 -Dmaven.tomcat.path=/sdp
```

주의할 점이 두 가지 있다.

- `web.xml` 의 `security-constraint`(`CONFIDENTIAL`)가 모든 요청을 `https://호스트/...` 로 돌린다.
  로컬에서 HTTP 로 보려면 이 블록을 잠시 주석 처리하거나, HTTPS 커넥터에 인증서를 붙여야 한다.
  **주석 처리했다면 반드시 되돌릴 것.**
- 화면 대부분은 DB(`192.168.14.50`, 사내망)가 필요하다. 사내망 밖에서는 로그인 화면까지만 뜬다.

2026-09-23 에 여기까지 확인했다(사내망 밖).

| 확인 | 결과 |
|---|---|
| 앱 기동(Spring 컨텍스트) | 성공. 기동 로그에 예외 없음 → 새로 추가한 `ProdReqService`·`MailService`·`FileService` 주입도 정상 |
| 로그인 화면(`sdpz000901u.do`) | HTTP 200, 정상 렌더링 |
| 로그인 전 보호 화면(`sdpa004001l.do` 등) | 로그인 화면으로 302 (세션 인터셉터 정상) |
| 삭제한 JavaScript(`common_bak2.js` 등) | 404. 남긴 `common.js`·`common_ui.js` 는 200 |
| **로그인 후 화면 동작** | **확인 못 함(DB 미접속)** |

### 배포 전 수동 확인 항목

Tomcat에서 실제로 띄워 확인하지 않았다. 배포 전 아래 화면을 확인한다.

DB(`192.168.14.50`)가 사내망에 있어 외부에서는 접속되지 않는다. 사내망에서 war 를 올린 뒤 아래를 확인한다.

공통 유틸 관련
- [ ] 시험성적서(Sdpe0010), MSDS(Sdpe0020) 화면의 메일 발송
- [ ] 공지사항(Sdpy0010) 첨부파일 등록, 변경, 삭제
- [ ] 날짜·금액 검색 조건이 있는 목록 화면 (예: Sdpf0030)
- [ ] 연도 기본값이 들어가는 화면 (Sdpb0010, Sdpd0010)
- [ ] 모든 화면의 JavaScript 동작 (`common.js`, `common_ui.js` 외 파일 삭제)

샘플의뢰 (`aabfcc9`)
- [ ] 샘플의뢰(Sdph0050) 목록·상세·작성·수정·삭제
- [ ] 샘플진도/결과(Sdph0052) 목록·상세·작성·수정·삭제

제조의뢰 (`4afe2e8`)
- [ ] 제조의뢰(sdpa004001l, sdpa004101d/u) 목록·상세·작성·수정·삭제
- [ ] 신규제조의뢰(sdpa004001d/u) 상세·작성·수정·삭제

## 7. 접속 정보 분리

커밋 참고: DB·메일 계정 정보를 XML 에서 빼고 `classpath:config/sdp.properties` 에서 읽는다.

| 파일 | 커밋 여부 | 내용 |
|---|---|---|
| `sdp/src/main/resources/config/sdp.properties.sample` | 커밋함 | 키 목록과 설명. 값은 자리표시자 |
| `sdp/src/main/resources/config/sdp.properties` | **커밋 안 함**(`.gitignore`) | 실제 접속 정보 |

새 환경에서 빌드할 때는 먼저 복사해서 값을 채운다.

```bash
cp sdp/src/main/resources/config/sdp.properties.sample sdp/src/main/resources/config/sdp.properties
```

이 파일이 없으면 Spring 컨텍스트가 뜨지 않고 아래 오류가 난다(잘못된 값으로 조용히 기동되지 않는다).

```
BeanInitializationException: Could not load properties;
  class path resource [config/sdp.properties] cannot be opened because it does not exist
```

> **아직 남은 문제:** 기존 비밀번호는 git 이력(`788bee4` 이전 커밋들)과 GitHub 원격에 그대로 남아 있다.
> 파일에서 뺀 것만으로는 사라지지 않으므로, DB(`hjbizpower`)와 메일(`injumaster@kangnam.co.kr`)
> 계정의 비밀번호를 교체해야 실제로 안전해진다.

## 8. 남은 정리 거리

- 긴 컨트롤러: `Sdpa0020`(495줄), `Sdpe0010`·`Sdpe0020`(각 446줄). `Sdpa0040` 과 같은 방식으로 분리할 수 있다.
- `common.js`(857줄), `common_ui.js`(620줄) 기능별 분리.
- 중복 JSP: `sdph005001u.jsp`(838줄)와 `sdph005201u.jsp`(811줄)는 약 820줄 중 79줄만 다르다.
- `common_include.jsp` 가 `jquery.toast.js` 와 `jquery.toast.min.js` 를 둘 다 불러온다(같은 라이브러리 2번).
- 나머지 컨트롤러에 남아 있는 `System.out.println` (약 42줄).
- 라이브러리가 모두 지원 종료 버전이다(Spring 3.1.1, iBatis 2, log4j 1.x, commons-dbcp 1.x, ojdbc14).

## 9. 죽은 JavaScript 삭제

커밋 `4f69f62`. 어디서도 부르지 않는 파일 6개(1,987줄)를 지웠다.

| 파일 | 줄 수 | 상태 |
|---|---|---|
| `common_bak2.js` | 1,239 | 부르는 곳 없음 |
| `common_bak1.js` | 708 | 부르는 곳 없음 |
| `validation.js` | 20 | `common_include.jsp` 에서 주석 처리된 줄뿐. 없는 파일(`common_chrome.js`)을 부르던 옛 브라우저 분기 |
| `common_ie.js`, `common_firefox.js` | 0 | 빈 파일. `validation.js` 만 참조 |
| `utils.js` | 12 | 부르는 곳 없음 (차트 색상 샘플) |

`common_include.jsp` 에서 `src` 가 아니라 `href` 로 되어 있어 아무것도 불러오지 않던 jQuery CDN `<script>` 태그도 함께 지웠다. 실제로 쓰는 `common.js`, `common_ui.js` 는 그대로다.

## 10. 샘플의뢰 화면 중복 제거 (Sdph0050/Sdph0052)

커밋 `aabfcc9`. 두 컨트롤러는 화면 번호만 바꿔서 비교하면 625줄 중 145줄만 달랐다.

다만 **서로 다른 저장 프로시저를 다른 파라미터 이름으로 호출**한다
(`procedure_selectSampleRequest` ↔ `procedure_selectSampleResult`, `ARG_REQ_DT` ↔ `ARG_DT`).
그래서 메서드 전체를 합치지 않고, 값이 완전히 같은 부분만 뽑았다.

- 네 번 반복되던 샘플의뢰 헤더 파라미터(35개)를 [`SampleRequestParams`](../src/main/java/com/jebi/sdp/common/SampleRequestParams.java) 의 `forInsert` / `forUpdate` 로 분리했다.
- `System.out.println` 96줄을 지웠다. 그중 `sampleRequestItemList.get(0)` 을 호출하던 6줄은 **조회 결과가 비어 있으면 예외가 나던 코드**라, 지우면서 그 오류 가능성도 없어졌다.
- `catch` 블록의 stdout 출력은 `logger.error` 로 바꿔 로그 파일에 남게 했다.

> **확인이 필요한 기존 동작:** 작성(INSERT)은 날짜를 `getExpDateString` 으로 `20250102` 형태로 넘기고,
> 수정(update)은 화면 입력값(`2025-01-02`)을 그대로 넘긴다. 같은 프로시저를 호출하는데도 다르다.
> 의도된 것인지 알 수 없어 동작은 그대로 두고 테스트로 고정만 했다. 저장 프로시저가 두 형식을 모두 받는지 확인할 것.

## 11. 제조의뢰 화면 서비스 분리 (Sdpa0040)

커밋 `4afe2e8`. 컨트롤러 644줄 → 336줄, [`ProdReqService`](../src/main/java/com/jebi/sdp/service/ProdReqService.java) 300줄.

- 저장 프로시저 호출 16개를 서비스로 옮기고, 컨트롤러에는 화면 흐름만 남겼다.
- 저장 계열 메서드는 프로시저의 `OUT_PARAM` 을 그대로 돌려준다. `"OK"` 가 아니면 호출하는 쪽에서 처리한다.
- 트랜잭션 경계는 기존처럼 컨트롤러가 관리한다(`startTransaction` ~ `endTransaction`).
- 8번 반복되던 `OUT_PARAM` 검사와 오류 처리를 `rollbackToError` 하나로 모았다.

검증: 프로시저 호출 24곳에서 **어떤 프로시저에 어떤 파라미터 키와 값을 넘기는지**를
원본(`git show HEAD~1:...`)과 스크립트로 대조해 전부 일치하는 것을 확인했다.

> **달라지는 동작:** 프로시저가 `OUT_PARAM` 을 `null` 로 돌려주는 경우.
> 기존 코드(`!map.get("OUT_PARAM").equals("OK")`)는 NPE 가 났고, 지금은 오류 화면으로 간다.
> 작성·수정은 원래도 `catch` 에서 오류 화면으로 갔으므로 같지만,
> 삭제는 `try/catch` 가 없어 기존에는 서버 오류(500)였다.
