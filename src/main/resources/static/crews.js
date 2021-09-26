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
    // getReviewers();
}

function getCrews() {
    fetch(crewsApi, {
        method: 'GET'
    }).then(function (data) {
            const crewNames = document.querySelector(".crews");
            if (data.status === 200) {
                data.json().then(res => {
                    crewNames.innerHTML += '백엔드 크루' + ': ';
                    for (let i = 0; i < res.backend.length; i++) {
                        crewNames.innerHTML += res.backend[i] + ' ';
                    }
                    crewNames.innerHTML += '<br>' + '프론트엔드 크루' + ': ';
                    for (let i = 0; i < res.frontend.length; i++) {
                        crewNames.innerHTML += res.frontend[i] + ' ';
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
    let part = document.querySelector("#inputPart").value;

    fetch(crewsApi + '?name=' + name + '&part=' + part, {
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

document.querySelector("#match").addEventListener("click", function () {
    fetch(reviewersApi, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
        }
    }).then(function (data) {
        console.log(data);
            const reviewerResults = document.querySelector(".reviewers");
            if (data.status === 200) {
                data.json().then(res => {
                    for (let i = 0; i < res.backendReviewers.length; i++) {
                        reviewerResults.innerHTML += res.backendReviewers[i] + '<br/>';
                    }
                    reviewerResults.innerHTML += '<br/>';
                    for (let i = 0; i < res.frontendReviewers.length; i++) {
                        reviewerResults.innerHTML += res.frontendReviewers[i] + '<br/>';
                    }

                })
            } else {
                alert('매칭 도중 오류가 발생했습니다.');
            }
        }
    );
});
