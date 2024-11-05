let idCheck = false;
let nicknameCheck = false;
let pwCheck = false;
let addressCheck = false;
let emailCheck = false;

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
const messageIdSpan = document.getElementById("idCheckMessage");

// 아이디에서 포커스가 빠지면 아이디 중복 체크 검사
// 포커스가 빠질때마다 서버통신하면 낭비
// 포커스가 빠지고나서 사용가능, 불가능 아이디를 저장해두고
// 다시 포커스가 빠질때 검사하려는 아이디가 기존의 아이디와 다르다면 서버 통신
let idValue = inputIdField.value;
inputIdField.addEventListener("blur", function() {
    // 포커스가 빠지고 기존에 가져왔던 아이디와
    // 현재 포커스가 빠진 시점의 아이디가 다르다면 서버 통신
    let focusOutId = inputIdField.value;

    // 아이디를 입력안하고 포커스를 빠졌을 떄
    if (isEmpty(focusOutId)) {
        messageIdSpan.style.display = "block";
        messageIdSpan.style.color = "red";
        messageIdSpan.textContent = "아이디를 입력해주세요.";
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
                    messageIdSpan.style.display = "block";
                    messageIdSpan.style.color = "red";
                    messageIdSpan.textContent = "이미 사용 중인 아이디입니다.";
                    // 사용 가능 아이디라면 아이디 입력칸 밑에 파란색으로 표기
                } else {
                    messageIdSpan.style.display = "block";
                    messageIdSpan.style.color = "blue";
                    messageIdSpan.textContent = "사용 가능한 아이디입니다.";
                }
                idValue = focusOutId;
            })
            .catch(error => {
                messageIdSpan.style.display = "block";
                messageIdSpan.style.color = "red";
                messageIdSpan.textContent = "에러 발생!! 문의 부탁드립니다.";
            });
    }
});

//=======================================================================
// 닉네임 중복 체크 로직

// 닉네임 입력 input
const inputNicknameField = document.getElementById("nickname");
// 닉네임 입력란 밑에 표시해줄 메세지 span
const messageNicknameSpan = document.getElementById("nicknameCheckMessage");
let nicknameValue = inputNicknameField.value;

inputNicknameField.addEventListener("blur", function() {
    let focusOutNickname = inputNicknameField.value;

    if (isEmpty(focusOutNickname)) {
        messageNicknameSpan.style.display = "block";
        messageNicknameSpan.style.color = "red";
        messageNicknameSpan.textContent = "닉네임를 입력해주세요.";
        return
    }

    if (nicknameValue !== focusOutNickname) {
        // 서버로 GET 요청을 보냄
        fetch(`/user/check/nickname/${focusOutNickname}`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            }
        })
            .then(response => response.json())
            .then(data => {
                // 중복된 아이디라면 아이디 입력칸 밑에 빨간색으로 표기
                if (data) {
                    messageNicknameSpan.style.display = "block";
                    messageNicknameSpan.style.color = "red";
                    messageNicknameSpan.textContent = "이미 사용 중인 닉네임입니다.";
                    // 사용 가능 아이디라면 아이디 입력칸 밑에 파란색으로 표기
                } else {
                    messageNicknameSpan.style.display = "block";
                    messageNicknameSpan.style.color = "blue";
                    messageNicknameSpan.textContent = "사용 가능한 닉네임입니다.";
                }
                nicknameValue = focusOutNickname;
            })
            .catch(error => {
                messageNicknameSpan.style.display = "block";
                messageNicknameSpan.style.color = "red";
                messageNicknameSpan.textContent = "에러 발생!! 문의 부탁드립니다.";
            });
    }
});


//=================================
// 비밀번호

// 비밀번호 입력 input
const inputPwField = document.getElementById("pw");
const inputPwCheckField = document.getElementById("pwCheck");
// 아이디 입력란 밑에 표시해줄 메세지 span
const messagePwSpan = document.getElementById("pwMessage");
const messagePwCheckSpan = document.getElementById("pwCheckMessage");

const regex = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[~!@#$%^&*()+|=])[A-Za-z\d~!@#$%^&*()+|=]{8,16}$/;

function validatePw() {
    let pw = inputPwField.value;
    let pwCheck = inputPwCheckField.value


    if (isEmpty(pw)) {
        messagePwSpan.style.display = "block";
        messagePwSpan.style.color = "red";
        messagePwSpan.textContent = "비밀번호를 입력해주세요.";
        return;
    }

    if (!regex.test(pw)) {
        messagePwSpan.style.display = "block";
        messagePwSpan.style.color = "red";
        messagePwSpan.textContent = "비밀번호는 영문, 숫자, 특수문자가 최소 1개씩 들어가야합니다. (8~16)";
    } else {
        messagePwSpan.style.display = "";
        messagePwSpan.style.color = "";
        messagePwSpan.textContent = "";
    }

    if (pw !== pwCheck) {
        messagePwCheckSpan.style.display = "block";
        messagePwCheckSpan.style.color = "red";
        messagePwCheckSpan.textContent = "비밀번호가 다릅니다.";
    } else {
        messagePwSpan.style.display = "none";
        messagePwSpan.style.color = "";
        messagePwSpan.textContent = "";
        messagePwCheckSpan.style.display = "";
        messagePwCheckSpan.style.color = "";
        messagePwCheckSpan.textContent = "";
    }
}
inputPwField.addEventListener("blur", validatePw);
inputPwCheckField.addEventListener("blur", validatePw);


// 이메일 중복 체크
// 이메일 정규식
const emailRegex = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/i;
// 이메일 입력 input
const inputEmailField = document.getElementById("email");
// 이메일 입력란 밑에 표시해줄 메세지 span
const messageEmailSpan = document.getElementById("emailCheckMessage");

let emailValue = inputEmailField.value;
inputEmailField.addEventListener("blur", function() {
    let focusOutEmail = inputEmailField.value;

    if (isEmpty(focusOutEmail)) {
        messageEmailSpan.style.display = "block";
        messageEmailSpan.style.color = "red";
        messageEmailSpan.textContent = "이메일을 입력해주세요.";
        return;
    }

    if (!emailRegex.test(focusOutEmail)) {
        messageEmailSpan.style.display = "block";
        messageEmailSpan.style.color = "red";
        messageEmailSpan.textContent = "이메일의 형식이 아닙니다.";
        return;
    }

    // 포커스가 빠질때 전에 입력해둔 아이디와 다를때만 서버통신
    if (emailValue !== focusOutEmail) {
        // 서버로 GET 요청을 보냄
        fetch(`/user/check/email/${focusOutEmail}`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            }
        })
            .then(response => response.json())
            .then(data => {
                // 중복된 아이디라면 아이디 입력칸 밑에 빨간색으로 표기
                if (data) {
                    messageEmailSpan.style.display = "block";
                    messageEmailSpan.style.color = "red";
                    messageEmailSpan.textContent = "이미 사용 중인 이메일입니다.";
                    // 사용 가능 아이디라면 아이디 입력칸 밑에 파란색으로 표기
                } else {
                    messageEmailSpan.style.display = "block";
                    messageEmailSpan.style.color = "blue";
                    messageEmailSpan.textContent = "사용 가능한 이메일입니다.";
                }
                emailValue = focusOutEmail;
            })
            .catch(error => {
                console.log(error);
                messageEmailSpan.style.display = "block";
                messageEmailSpan.style.color = "red";
                messageEmailSpan.textContent = "에러 발생!! 문의 부탁드립니다.";
            });
    }
});