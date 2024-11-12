// 사용자 조회 (ID)
const userSearchBtn = document.getElementById('user-search-btn');
const userId = document.getElementById('user-id-input').value;

if (userSearchBtn) {
    userSearchBtn.addEventListener('click', event => {
        if (userId.trim() === "") {
            alert("ID를 입력하세요.");
            return;
        }

        fetch(`/admin/user/${userId}`, {
            method: 'GET',
        }).then(response => {
            if (!response.ok) {
                throw new Error("해당 사용자를 찾을 수 없습니다.");
            }
            return response.json();
        }).catch(error => {
            console.error("Error: ", error);
            alert("해당 사용자 조회에 실패했습니다.");
        });
    });
}

// 모든 사용자 조회
const searchAllUserBtn = document.getElementById('all-user-btn');

if (searchAllUserBtn) {
    searchAllUserBtn.addEventListener('click', event => {

    });
}