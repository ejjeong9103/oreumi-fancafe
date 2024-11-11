let nicknameCheck = false;
let pwCheck = false;
let addressCheck = false;

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
            let zoneCodeElement = document.getElementById("newPost");
            let addrElement = document.getElementById("newAddress");
            zoneCodeElement.value = data.zonecode;
            addrElement.value = addr;
            addressCheck = true;
            document.getElementById("newAddressDetail").focus();
        }
    }).open();
}

function deletePost(event) {
    event.preventDefault();
    let zoneCodeElement = document.getElementById("newPost");
    let addrElement = document.getElementById("newAddress");
    zoneCodeElement.value = "";
    addrElement.value = "";
    addressCheck = false;
}


//=======================================================================
// 닉네임 중복 체크 로직

// 닉네임 입력 input
const inputNicknameField = document.getElementById("nickname");
// 닉네임 입력란 밑에 표시해줄 메세지 span
const messageNicknameSpan = document.getElementById("nicknameCheckMessage");
let nicknameValue = inputNicknameField.value;
const nicknameRegex = /^[a-zA-Z가-힣][a-zA-Z가-힣0-9]{5,13}$/;

inputNicknameField.addEventListener("blur", function() {
    let focusOutNickname = inputNicknameField.value;

    if (!isEmpty(focusOutNickname)) {
        if (!nicknameRegex.test(focusOutNickname)) {
            messageNicknameSpan.style.display = "block";
            messageNicknameSpan.style.color = "red";
            messageNicknameSpan.textContent = "영문 또는 한글로 시작해야하며 자릿수를 확인해주세요. (6~14)";
            nicknameCheck = false;
            return
        } else {
            messageNicknameSpan.style.display = "";
            messageNicknameSpan.style.color = "";
            messageNicknameSpan.textContent = "";
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
                        nicknameCheck = false;
                        // 사용 가능 아이디라면 아이디 입력칸 밑에 파란색으로 표기
                    } else {
                        messageNicknameSpan.style.display = "block";
                        messageNicknameSpan.style.color = "blue";
                        messageNicknameSpan.textContent = "사용 가능한 닉네임입니다.";
                        nicknameCheck = true;
                    }
                    nicknameValue = focusOutNickname;
                })
                .catch(error => {
                    messageNicknameSpan.style.display = "block";
                    messageNicknameSpan.style.color = "red";
                    messageNicknameSpan.textContent = "에러 발생!! 문의 부탁드립니다.";
                    nicknameCheck = false;
                });
        }
    }
});


//=================================
// 비밀번호

// 비밀번호 입력 input
const inputPwField = document.getElementById("newPassword");
const inputPwCheckField = document.getElementById("newPasswordCheck");
// 아이디 입력란 밑에 표시해줄 메세지 span
const messagePwSpan = document.getElementById("pwMessage");
const messagePwCheckSpan = document.getElementById("pwCheckMessage");

const regex = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[~!@#$%^&*()+|=])[A-Za-z\d~!@#$%^&*()+|=]{8,16}$/;

function validatePw() {
    let pwValue = inputPwField.value;
    let pwCheckValue = inputPwCheckField.value

    if (!regex.test(pwValue)) {
        messagePwSpan.style.display = "block";
        messagePwSpan.style.color = "red";
        messagePwSpan.textContent = "비밀번호는 영문, 숫자, 특수문자가 최소 1개씩 들어가야합니다. (8~16)";
        pwCheck = false;
        return
    } else {
        messagePwSpan.style.display = "";
        messagePwSpan.style.color = "";
        messagePwSpan.textContent = "";
    }

    if (pwValue !== pwCheckValue) {
        messagePwCheckSpan.style.display = "block";
        messagePwCheckSpan.style.color = "red";
        messagePwCheckSpan.textContent = "비밀번호가 다릅니다.";
        pwCheck = false;
    } else {
        messagePwSpan.style.display = "none";
        messagePwSpan.style.color = "";
        messagePwSpan.textContent = "";
        messagePwCheckSpan.style.display = "";
        messagePwCheckSpan.style.color = "";
        messagePwCheckSpan.textContent = "";
        pwCheck = true;
    }
}
inputPwField.addEventListener("blur", validatePw);
inputPwCheckField.addEventListener("blur", validatePw);

function preSubmitCheck() {
    // 만약 비어있는 값이 있다면 false로 바꾸기
    if (isEmpty(inputNicknameField.value)) {
        nicknameCheck = false;
    }

    if (isEmpty(inputPwField.value) || isEmpty(inputPwCheckField.value)) {
        pwCheck = false;
        inputPwField.value = "";
        inputPwCheckField.value = "";
    }

    if (!nicknameCheck && !pwCheck && !addressCheck) {
        alert("변경하려는 내용이 없거나 양식에 맞지 않습니다.");
    } else {
        const formData = new FormData(document.getElementById("updateForm"));
        const userId = document.getElementById("userId").value;
        // 비동기 요청
        fetch(`/user/${userId}`, {
            method: "PUT",
            body: formData
        })
            .then(response => {
                if (!response.ok) {
                    return response.text().then(errorMessage => {
                        throw new Error(errorMessage);  // 에러 메시지를 던져 catch로 전달
                    });
                }
                return response.text(); // 성공 시 응답 텍스트 반환
            })
            .then(message => {
                alert(message);
                window.location.href = "/";  // 성공 시 메인 페이지로 이동
            })
            .catch(error => {
                alert(error);
            });
    }
}

function deleteUser() {
    // 확인 팝업 띄우기
    const isConfirmed = confirm("정말로 삭제하시겠습니까?");
    if (!isConfirmed) {
        return; // 사용자가 '아니오'를 선택하면 함수 종료
    }
    // 비동기 요청
    fetch("/user/deleteUser", {
        method: "PUT",
        body: formData
    })
        .then(response => {
            if (!response.ok) {
                return response.text().then(errorMessage => {
                    throw new Error(errorMessage);  // 에러 메시지를 던져 catch로 전달
                });
            }
            return response.text(); // 성공 시 응답 텍스트 반환
        })
        .then(message => {
            alert(message);
            window.location.href = "/";  // 성공 시 메인 페이지로 이동
        })
        .catch(error => {
            alert(error);
        });
}