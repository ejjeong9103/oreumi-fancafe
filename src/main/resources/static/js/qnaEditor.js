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

    // 글 작성 버튼에 이벤트 리스너 추가

    if (createButton) {
        createButton.addEventListener('click', event => {
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
            }).then(response=>{
                if (!response.ok) {
                    throw new Error('서버 응답이 올바르지 않습니다.');
                }
                return response.json();
            })
                .then(() => {
                    alert('등록 완료되었습니다');
                    location.replace(`/myPage`);
                })
                .catch((error)=>{
                    console.log('오류:',error);
                });
        });
    }
});

