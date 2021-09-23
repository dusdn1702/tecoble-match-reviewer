let crewsPage;
let crewsApi;

document.addEventListener("DOMContentLoaded", function () {
    crewsPage = new CrewsPage();
});

function CrewsPage() {
    crewsApi = window.location.origin + "/api/crews";
    getCrews();
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
                crewNames.innerHTML += '등록된 크루가 없습니다.';
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
