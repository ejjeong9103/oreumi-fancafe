// 사용자 조회 (ID)
const userSearchBtn = document.getElementById('user-search-btn');

if (userSearchBtn) {
    userSearchBtn.addEventListener('click', event => {
        const userId = document.getElementById('user-id-input').value;

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

// 사용자 상세 정보 표시
const userInfoBtn = document.getElementById('btncheck1');

if (userInfoBtn) {
    userInfoBtn.addEventListener('change', function() {
        const userInfoDiv = document.querySelector('.user-info');
        if (this.checked) {
            userInfoDiv.style.display = 'block';
        }
        else {
            userInfoDiv.style.display = 'none';
        }
    });
}

// 신고 및 문의 내역
const showHelpBtn = document.getElementById('btncheck2');

if (showHelpBtn) {
    showHelpBtn.addEventListener('change', function() {
        const helpDiv = document.querySelector('.find-help');
        const userId = document.getElementById('user-id-input').value;
        if (this.checked) {
            helpDiv.style.display = 'block';

            fetch(`/admin/help/${userId}`, {
                method: 'GET',
            }).then(() => {
                alert("신고 및 문의 내역을 불러왔습니다.");
            });
        }
        else {
            helpDiv.style.display = 'none';
        }
    });
}

// 사용자 등급 변경란 표시
const changeRoleBtn = document.getElementById('btncheck3');

if (changeRoleBtn) {
    changeRoleBtn.addEventListener('change', function() {
        const changeRoleDiv = document.querySelector('.change-role');
        if (this.checked) {
            changeRoleDiv.style.display = 'block';
        }
        else {
            changeRoleDiv.style.display = 'none';
        }
    });
}

// 사용자 상태 변경란 표시
const changeStateBtn = document.getElementById('btncheck4');

if (changeStateBtn) {
    changeStateBtn.addEventListener('change', function() {
        const changeStateDiv = document.querySelector('.change-state');
        if (this.checked) {
            changeStateDiv.style.display = 'block';
        }
        else {
            changeStateDiv.style.display = 'none';
        }
    });
}

// 사용자 상태 변경
const changeUserStateBtn = document.getElementById('change-state-btn');

if (changeUserStateBtn) {
    changeUserStateBtn.addEventListener('click', event => {
        const selectedState = document.getElementById('change-state-select').value;
        const userId = document.getElementById('user-id-input').value;

        fetch(`/admin/user/${userId}/userState`, {
            method: 'PUT',
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({state: selectedState})
        }).then(() => {
            alert('해당 사용자의 상태를 변경했습니다.');
        });
    });
}