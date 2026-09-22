# 공통 유틸 구조 (`com.jebi.sdp.common`)

2026-09-22 `refactor/commonutil` 브랜치에서 1,668줄짜리 `CommonUtil`을 분리·정리한 결과를 기록한다.

## 1. 요약

| 이전 | 이후 |
|---|---|
| 컨트롤러 29개가 `extends CommonUtil` 로 유틸 메서드를 상속 | 상속 없음. static 메서드는 `import static`, 메일·파일은 서비스 주입 |
| `CommonUtil` 한 클래스에 문자열·날짜·암호화·메일·파일·외부 API 호출 약 80개 메서드 | 용도별 클래스 5개, 실제로 호출되는 메서드 7개만 유지 |
| 테스트 없음 | `src/test` 에 JUnit 테스트 5개 |

`CommonUtil.java`, `CryptoUtil.java` 는 삭제되었다.

## 2. 클래스 구성

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

## 3. 컨트롤러에서 사용하는 방법

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

## 4. 삭제된 코드

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

> 테스트 실행 시 로그 설정의 Windows 경로 때문에 작업 폴더에 `C:\logs\error\error.log` 파일이 생길 수 있다. 커밋하지 말고 지운다.

### 배포 전 수동 확인 항목

Tomcat에서 실제로 띄워 확인하지 않았다. 배포 전 아래 화면을 확인한다.

- [ ] 시험성적서(Sdpe0010), MSDS(Sdpe0020) 화면의 메일 발송
- [ ] 공지사항(Sdpy0010) 첨부파일 등록, 변경, 삭제
- [ ] 날짜·금액 검색 조건이 있는 목록 화면 (예: Sdpa0040, Sdpf0030)
- [ ] 연도 기본값이 들어가는 화면 (Sdpb0010, Sdpd0010)

## 7. 남은 정리 거리

(1~5단계 이후 정리한 항목은 `c94e09d`, `3396291`, 아래 `null` 처리 커밋에서 마무리했다.)

- `CommonUtil` 다음으로 큰 파일: `common_bak2.js`(1,239줄) 등 JavaScript 정리는 아직 하지 않았다.
