document.addEventListener('DOMContentLoaded', function () {
    const answerButton = document.querySelector('.answer-btn');
    if (answerButton) {
        const currentUrl = window.location.href;
        const id = parseInt(currentUrl.split('/question/')[1], 10);
        answerButton.addEventListener('click', (e) => {
            e.preventDefault();
            window.location.href = `/answer/${id}`;
        });
    } else {
        console.error('answer-btn 요소를 찾을 수 없습니다.');
    }
});