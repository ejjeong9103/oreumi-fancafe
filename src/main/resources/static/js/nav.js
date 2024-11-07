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
    document.getElementById("login").addEventListener("click", function (event) {
        login(event);
    })
    document.getElementById("my-page-button").addEventListener("click", function (event) {
        location.href="/user/myPage";
    })
});