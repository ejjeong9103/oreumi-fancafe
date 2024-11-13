let isEmpty = (txt) => {
    return txt == null || txt === "";
}

function login(event) {
    const userId = document.getElementById("userId").value;
    const userPw = document.getElementById("userPw").value;

    if (isEmpty(userId)) {
        alert("아이디를 입력해주세요.");
        event.preventDefault();
        return
    }

    if (isEmpty(userPw)) {
        alert("비밀번호를 입력해주세요.")
        event.preventDefault();
        return
    }

    const formData = new FormData(document.getElementById("login-form"));

    // 비동기 요청
    fetch("/user/login", {
        method: "POST",
        body: formData
    })
        .then(response => {
            if (!response.ok) {
                return response.json().then(errorMessage => {
                    alert(errorMessage.message);
                    throw new Error();
                });
            }
            return response.text(); // 성공 시 응답 텍스트 반환
        })
        .then(message => {
            window.location.href = "/";  // 성공 시 메인 페이지로 이동
        })
        .catch(error => {
        });
}

document.addEventListener("DOMContentLoaded", function () {
    let loginField = document.getElementById("login");
    if (loginField != null) {
        loginField.addEventListener("click", function (event) {
            login(event);
        })
    }

    // Enter 키로 로그인 활성화
    const form = document.getElementById("login-form");
    if (form != null) {
        form.addEventListener("keydown", function (event) {
            if (event.key === "Enter") {
                event.preventDefault(); // 폼의 기본 동작 방지
                login(event);
            }
        });
    }
    // document.getElementById("my-page-button").addEventListener("click", function (event) {
    //     location.href="/user/myPage";
    // })
});