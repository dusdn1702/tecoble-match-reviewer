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
    getReviewers();
}

function getCrews() {
    fetch(crewsApi, {
        method: 'GET'
    }).then(function (data) {
            const crewNames = document.querySelector(".crews");
            if (data.status === 200) {
                data.json().then(res => {
                    for (let i = 0; i < res.crews.length; i++) {
                        crewNames.innerHTML += res.crews[i] + ' ';
                    }
                })
            } else {
                crewNames.innerHTML += '등록 결과가 없습니다.';
            }
        }
    );
}

document.querySelector("#register").addEventListener("click", function () {
    let name = document.querySelector("#inputName").value;
    fetch(crewsApi + '?name=' + name, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        }
    }).then(function (response) {
        if (response.status === 200) {
            location.reload();
        } else {
            alert('중복된 이름이 존재합니다.');
        }
    });
});

function getReviewers() {
    fetch(reviewersApi, {
        method: 'GET'
    }).then(function (data) {
            const reviewerResults = document.querySelector(".reviewers");
            if (data.status === 200) {
                data.json().then(res => {
                    for (let i = 0; i < res.reviewers.length; i++) {
                        reviewerResults.innerHTML += '- ' + res.reviewers[i] + '<br/>';
                    }
                })
            } else {
                reviewerResults.innerHTML += '매칭 결과가 없습니다.';
            }
        }
    );
}

document.querySelector("#match").addEventListener("click", function () {
    fetch(reviewersApi, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        }
    }).then(function (response) {
        if (response.status === 200) {
            location.reload();
        } else {
            alert('매칭 도중 오류가 발생했습니다.');
        }
    });
});
