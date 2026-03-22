let allData = [];

const API = "http://localhost:8080/api/files";


// ✅ Upload (FILE + URL both)
function upload() {

    let title = document.getElementById("title").value;
    let category = document.getElementById("category").value;
    let fileInput = document.getElementById("file").files[0];
    let fileUrl = document.getElementById("fileUrl").value;

    if (title === "") {
        alert("Title required ❌");
        return;
    }

    // 🔥 FILE upload
    if (fileInput) {

        let formData = new FormData();
        formData.append("file", fileInput);
        formData.append("title", title);
        formData.append("category", category);

        fetch(API + "/upload", {
            method: "POST",
            body: formData
        })
        .then(res => res.json())
        .then(() => {
            alert("File Uploaded ✅");
            loadAdminData();
        });

    } 
    // 🔥 URL upload
    else if (fileUrl !== "") {

        let data = {
            title: title,
            category: category,
            fileUrl: fileUrl
        };

        fetch(API, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(data)
        })
        .then(res => res.json())
        .then(() => {
            alert("URL Uploaded ✅");
            loadAdminData();
        });
    } 
    else {
        alert("File ya URL do ❌");
    }
}


// ✅ Load Admin Data
function loadAdminData() {

    fetch(API)
        .then(res => res.json())
        .then(data => {

            let div = document.getElementById("list");
            if (!div) return;

            div.innerHTML = "";

            data.forEach(item => {

                let preview = item.fileUrl.endsWith(".pdf")
                    ? `<a href="${item.fileUrl}" target="_blank">Open PDF</a>`
                    : `<img src="${item.fileUrl}"/>`;

                div.innerHTML += `
                <div class="card">
                    <h3>${item.title}</h3>
                    <p>${item.category}</p>
                    ${preview}
                    <br><br>
                    <button onclick="deleteFile(${item.id})">Delete</button>
                </div>
                `;
            });
        });
}


// ✅ Delete
function deleteFile(id) {

    fetch(API + "/" + id, { method: "DELETE" })
        .then(() => {
            alert("Deleted ✅");
            loadAdminData();
        });
}


// ✅ Gallery Load
function loadGallery() {

    fetch(API)
        .then(res => res.json())
        .then(data => {
            allData = data;
            display(data);
        });
}


// ✅ Display
function display(data) {

    let div = document.getElementById("gallery");
    if (!div) return;

    div.innerHTML = "";

    data.forEach(item => {

        let preview = item.fileUrl.endsWith(".pdf")
            ? `<a href="${item.fileUrl}" target="_blank">Open PDF</a>`
            : `<img src="${item.fileUrl}"/>`;

        div.innerHTML += `
            <div class="card">
                <h3>${item.title}</h3>
                <p>${item.category}</p>
                ${preview}
            </div>
        `;
    });
}


// ✅ Filter
function filterCategory(cat) {

    if (cat === "All") {
        display(allData);
    } else {
        let filtered = allData.filter(x => x.category === cat);
        display(filtered);
    }
}


// ✅ Auto load
loadAdminData();
loadGallery();