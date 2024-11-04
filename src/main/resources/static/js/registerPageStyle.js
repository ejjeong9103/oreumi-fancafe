function execDaumPostcode(event) {
    event.preventDefault(); // 기본 폼 제출 방지
    new daum.Postcode({
        oncomplete: function(data) {
            // 팝업을 통한 검색 결과 항목 클릭 시 실행
            let addr = ''; // 주소_결과값이 없을 경우 공백
            let extraAddr = ''; // 참고항목

            //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
            if (data.userSelectedType === 'R') { // 도로명 주소를 선택
                addr = data.roadAddress;
            } else { // 지번 주소를 선택
                addr = data.jibunAddress;
            }

            if(data.userSelectedType === 'R'){
                if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                    extraAddr += data.bname;
                }
                if(data.buildingName !== '' && data.apartment === 'Y'){
                    extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                }
                if(extraAddr !== ''){
                    extraAddr = ' (' + extraAddr + ')';
                }
            }

            // 데이터 셋팅
            let zoneCodeElement = document.getElementById("post");
            let addrElement = document.getElementById("address");
            zoneCodeElement.value = data.zonecode;
            addrElement.value = addr;
            zoneCodeElement.style
            document.get
            document.getElementById("addressDetail").focus();
        }
    }).open();
}

// 회원 가입 validate
let isEmpty = (txt) => {
    return txt == null || txt === "";
}

// 아이디 중복 체크
// 아이디 입력 input
const inputIdField = document.getElementById("id");
// 아이디 입력란 밑에 표시해줄 메세지 span
const messageSpan = document.getElementById("idCheckMessage");

// 아이디에서 포커스가 빠지면 아이디 중복 체크 검사
// 포커스가 빠질때마다 서버통신하면 낭비
// 포커스가 빠지고나서 사용가능, 불가능 아이디를 저장해두고
// 다시 포커스가 빠질때 검사하려는 아이디가 기존의 아이디와 다르다면 서버 통신
let idValue = id.value;
inputIdField.addEventListener("blur", function() {
    // 포커스가 빠지고 기존에 가져왔던 아이디와
    // 현재 포커스가 빠진 시점의 아이디가 다르다면 서버 통신
    let focusOutId = id.value;

    // 아이디를 입력안하고 포커스를 빠졌을 떄
    if (isEmpty(focusOutId)) {
        messageSpan.style.display = "block";
        messageSpan.style.color = "red";
        messageSpan.textContent = "아이디를 입력해주세요.";
        return
    }

    // 포커스가 빠질때 전에 입력해둔 아이디와 다를때만 서버통신
    if (idValue !== focusOutId) {
        // 서버로 GET 요청을 보냄
        fetch(`/user/check/userid/${focusOutId}`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            }
        })
            .then(response => response.json())
            .then(data => {
                // 중복된 아이디라면 아이디 입력칸 밑에 빨간색으로 표기
                if (data) {
                    messageSpan.style.display = "block";
                    messageSpan.style.color = "red";
                    messageSpan.textContent = "이미 사용 중인 아이디입니다.";
                    // 사용 가능 아이디라면 아이디 입력칸 밑에 파란색으로 표기
                } else {
                    messageSpan.style.display = "block";
                    messageSpan.style.color = "blue";
                    messageSpan.textContent = "사용 가능한 아이디입니다.";
                }
                idValue = focusOutId;
            })
            .catch(error => {
                console.error("에러 발생:", error);
            });
    }
});

//=======================================================================
// 비밀번호 검사 로직