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
                    for (let i = 0; i < res.backendCrews.length; i++) {
                        crewNames.innerHTML += res.backendCrews[i] + ' ';
                    }
                    crewNames.innerHTML += '<br/>';
                    crewNames.innerHTML += '🖌 프론트엔드 크루 - ';
                    for (let i = 0; i < res.frontendCrews.length; i++) {
                        crewNames.innerHTML += res.frontendCrews[i] + ' ';
                    }
                })
            } else {
                crewNames.innerHTML += '등록 결과가 없습니다.';
            }
        }
    );
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
            alert('등록에 문제가 있습니다.');
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
            if(reviewerResults.innerHTML.length > 0) reviewerResults.innerHTML = "";
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
                alert('리뷰어 매칭을 위한 최소 인원은 3명입니다.');
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
            alert('등록에 문제가 있습니다.');
        }
    });
});
