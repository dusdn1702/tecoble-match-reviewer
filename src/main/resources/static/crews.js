let crewsPage;
let crewsApi;
let reviewersApi;

document.addEventListener("DOMContentLoaded", function () {
    crewsPage = new CrewsPage();
});

function CrewsPage() {
    crewsApi = window.location.origin + "/api/crews";
    reviewersApi = window.location.origin + "/api/reviewers";

    getCrews();
}

function getCrews() {
    fetch(crewsApi, {
        method: 'GET'
    }).then(function (data) {
            const crewNames = document.querySelector(".crews");
            if (data.status === 200) {
                data.json().then(res => {
                    crewNames.innerHTML += '🖋️ 백엔드 크루 - ';
                    crewNames.innerHTML += res.backendCrews.map(crew =>
                        `<span onclick=deleteCrew('${crew}')>${crew}</span>`
                    ).join(" ");
                    crewNames.innerHTML += '<br/>';
                    crewNames.innerHTML += '🖌 프론트엔드 크루 - ';
                    crewNames.innerHTML += res.frontendCrews.map(crew =>
                        `<span onclick=deleteCrew('${crew}')>${crew}</span>`
                    ).join(" ");
                })
            } else {
                crewNames.innerHTML += '크루를 등록해주세요.';
            }
        }
    );
}

function deleteCrew(crew) {
    fetch(crewsApi + '/' + crew, {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json',
        }
    }).then(function (response) {
        if (response.status === 200) {
            location.reload();
        } else {
            alert('삭제에 오류가 발생했습니다.');
        }
    });
}

document.querySelector("#saveCrew").addEventListener("click", function () {
    let name = document.querySelector("#name").value;
    let part = document.querySelector(".form-select").value;

    fetch(crewsApi + '?name=' + name + '&part=' + part, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        }
    }).then(function (response) {
        if (response.status === 200) {
            location.reload();
        } else {
            alert('등록에 오류가 발생했습니다.');
        }
    });
});

document.querySelector("#findReviewers").addEventListener("click", function () {
    fetch(reviewersApi, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
        }
    }).then(function (data) {
            const reviewerResults = document.querySelector(".reviewers");
            if (reviewerResults.innerHTML.length > 0) reviewerResults.innerHTML = "";
            if (data.status === 200) {
                data.json().then(res => {
                    reviewerResults.innerHTML += '🪐 백엔드<br/><br/>';
                    for (let i = 0; i < res.backendReviewers.length; i++) {
                        reviewerResults.innerHTML += res.backendReviewers[i] + '<br/>';
                    }
                    reviewerResults.innerHTML += '<br/><br/>';
                    reviewerResults.innerHTML += '🪐 프론트엔드<br/><br/>';
                    for (let i = 0; i < res.frontendReviewers.length; i++) {
                        reviewerResults.innerHTML += res.frontendReviewers[i] + '<br/>';
                    }
                    reviewerResults.innerHTML += '<br/>';
                })
            } else {
                alert('매칭에 오류가 발생했습니다. 최소 3명을 등록해주세요.');
            }
        }
    );
});

document.querySelector("#deleteAllCrew").addEventListener("click", function () {
    fetch(crewsApi, {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json',
        }
    }).then(function (response) {
        if (response.status === 200) {
            location.reload();
        } else {
            alert('초기화에 오류가 발생했습니다.');
        }
    });
});
