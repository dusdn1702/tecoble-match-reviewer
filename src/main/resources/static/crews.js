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
                    crewNames.innerHTML += 'ποΈ λ°±μλ ν¬λ£¨ - ';
                    crewNames.innerHTML += res.backendCrews.map(crew =>
                        `<span onclick=deleteCrew('${crew}')>${crew}</span>`
                    ).join(" ");
                    crewNames.innerHTML += '<br/>';
                    crewNames.innerHTML += 'π νλ‘ νΈμλ ν¬λ£¨ - ';
                    crewNames.innerHTML += res.frontendCrews.map(crew =>
                        `<span onclick=deleteCrew('${crew}')>${crew}</span>`
                    ).join(" ");
                })
            } else {
                crewNames.innerHTML += 'ν¬λ£¨λ₯Ό λ±λ‘ν΄μ£ΌμΈμ.';
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
            alert('μ­μ μ μ€λ₯κ° λ°μνμ΅λλ€.');
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
            alert('λ±λ‘μ μ€λ₯κ° λ°μνμ΅λλ€.');
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
                    reviewerResults.innerHTML += 'πͺ λ°±μλ<br/><br/>';
                    for (let i = 0; i < res.backendReviewers.length; i++) {
                        reviewerResults.innerHTML += res.backendReviewers[i] + '<br/>';
                    }
                    reviewerResults.innerHTML += '<br/><br/>';
                    reviewerResults.innerHTML += 'πͺ νλ‘ νΈμλ<br/><br/>';
                    for (let i = 0; i < res.frontendReviewers.length; i++) {
                        reviewerResults.innerHTML += res.frontendReviewers[i] + '<br/>';
                    }
                    reviewerResults.innerHTML += '<br/>';
                })
            } else {
                alert('λ§€μΉ­μ μ€λ₯κ° λ°μνμ΅λλ€. μ΅μ 3λͺμ λ±λ‘ν΄μ£ΌμΈμ.');
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
            alert('μ΄κΈ°νμ μ€λ₯κ° λ°μνμ΅λλ€.');
        }
    });
});
