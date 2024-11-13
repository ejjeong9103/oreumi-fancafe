document.addEventListener('DOMContentLoaded', function () {

    const button = document.querySelector('.dropdown-btn');
    const dropdownItems = document.querySelectorAll('.dropdown-menu .dropdown-item');
    let hiddenInput = document.getElementById('hiddenValue');
    const createButton = document.querySelector('.btn-success');

    // 각 드롭다운 항목에 클릭 이벤트 추가
    dropdownItems.forEach(item => {
        item.addEventListener('click', function (event) {
            event.preventDefault(); // 링크의 기본 동작 방지
            // 선택한 항목의 텍스트를 버튼 텍스트로 설정
            button.textContent = event.target.textContent;

            // 선택한 항목의 부모 <li>의 value 속성을 hidden input에 설정
            hiddenInput.value = item.parentElement.getAttribute('value');
        });
    });


    const currentUrl = window.location.href;
    let helpTypeInput = document.getElementById('helpType');
    let titleInput = document.getElementById('title');
    const originalHelpType = helpTypeInput.value;
    let helpTypeText = originalHelpType === '1' ? '문의' : '신고';

    // 답변 작성시 타입/제목 수정 불가능하게
    if (currentUrl.includes('/answer/')) {
        // URL에 "/edit/"이 포함되어 있으면 title 필드, hiddenvalue 고정, dropdown버튼 설정

        titleInput.value = titleInput.getAttribute('data-title');
        titleInput.readOnly = true;
        hiddenInput.value = originalHelpType;
        button.textContent = helpTypeText;

    } else { // URL에 "/edit/"이 포함되어 있지 않으면 title 필드를 편집 가능하게 합니다.
        titleInput.readOnly = false;

    }

    // 글 작성 버튼에 이벤트 리스너 추가
    if (createButton) {
        createButton.addEventListener('click', event => {
            if (currentUrl.includes('/answer/')) {
                const id = parseInt(currentUrl.split('/answer/')[1], 10);
                fetch(`/admin/answer/${id}`, {
                    method: 'PUT',
                    headers: {
                        "Content-Type": "application/json"
                    },
                    body: JSON.stringify({
                        answer: document.querySelector('.content').value
                        //     시큐리티 연결 후 admin id도 받아오도록
                    }),
                }).then(response => {
                    if (!response.ok) {
                        throw new Error('서버 응답이 올바르지 않습니다.');
                    }
                    return response.json();
                })
                    .then(data => {
                        alert('답변이 성공적으로 작성되었습니다.');
                        // 관리자 페이지로 수정해주세요.
                        location.replace(`/help/question/${id}`);
                    })
                    .catch((error) => {
                        console.error('오류:', error);
                    })
            } else {
                if (!hiddenInput.value){
                    alert('문의 유형을 선택해주세요.');
                    return;
                }
                fetch('/help/question', {
                    method: 'POST',
                    headers: {
                        "Content-type": "application/json"
                    },
                    body: JSON.stringify({
                        title: document.getElementById('title').value,
                        content: document.getElementById('content').value,
                        helpType: document.getElementById('hiddenValue').value
                    }),
                }).then(response => {
                    if (!response.ok) {
                        throw new Error('서버 응답이 올바르지 않습니다.');
                    }
                    return response.json();
                })
                    .then(data => {
                        const questionId = data.id;
                        alert('등록 완료되었습니다.');
                        location.replace(`/help/question/${questionId}`);
                    })
                    .catch((error) => {
                        console.log('오류:', error);
                    });
            }
        });
    }

});

