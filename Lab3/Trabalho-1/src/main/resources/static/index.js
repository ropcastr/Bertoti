const API_URL = "/alunos";

const modalAluno = document.getElementById("modalAluno");
const modalTitle = document.getElementById("modalTitle");
const alunoForm = document.getElementById("alunoForm");
const alunoIdInput = document.getElementById("alunoId");
const nomeAlunoInput = document.getElementById("nomeAluno");
const emailAlunoInput = document.getElementById("emailAluno");
const cursoAlunoInput = document.getElementById("cursoAluno");

const openModalBtn = document.getElementById("openModalBtn");
const closeModalBtn = document.getElementById("closeModalBtn");
const cancelModalBtn = document.getElementById("cancelModalBtn");
const searchInput = document.getElementById("searchInput");
const totalAlunos = document.getElementById("totalAlunos");
const alunoList = document.getElementById("aluno-list");
const btnRecarregar = document.getElementById("btnRecarregar");

let todosAlunos = [];

// Abrir modal para novo cadastro
function showModalNovo() {
    modalTitle.textContent = "Matricular Novo Aluno";
    alunoIdInput.value = "";
    nomeAlunoInput.value = "";
    emailAlunoInput.value = "";
    cursoAlunoInput.value = "";
    modalAluno.classList.add("show");
    setTimeout(() => nomeAlunoInput.focus(), 100);
}

// Abrir modal para edição
function showModalEditar(aluno) {
    modalTitle.textContent = "Editar Dados do Aluno";
    alunoIdInput.value = aluno.id;
    nomeAlunoInput.value = aluno.nome;
    emailAlunoInput.value = aluno.email;
    cursoAlunoInput.value = aluno.curso;
    modalAluno.classList.add("show");
    setTimeout(() => nomeAlunoInput.focus(), 100);
}

function hideModal() {
    modalAluno.classList.remove("show");
}

if (openModalBtn) openModalBtn.addEventListener("click", showModalNovo);
if (closeModalBtn) closeModalBtn.addEventListener("click", hideModal);
if (cancelModalBtn) cancelModalBtn.addEventListener("click", hideModal);
if (btnRecarregar) btnRecarregar.addEventListener("click", carregarAlunos);

window.addEventListener("click", (event) => {
    if (event.target === modalAluno) {
        hideModal();
    }
});

// Tratamento do formulário (POST para novo ou PUT para edição)
alunoForm.addEventListener("submit", (event) => {
    event.preventDefault();

    const id = alunoIdInput.value;
    const nome = nomeAlunoInput.value.trim();
    const email = emailAlunoInput.value.trim();
    const curso = cursoAlunoInput.value.trim();

    if (!nome || !email || !curso) {
        alert("Por favor, preencha todos os campos.");
        return;
    }

    const payload = { nome, email, curso };

    if (id) {
        // Modo Edição: PUT /alunos/{id}
        payload.id = id;
        axios.put(`${API_URL}/${id}`, payload)
            .then(() => {
                hideModal();
                carregarAlunos();
            })
            .catch((error) => {
                console.error("Erro ao atualizar aluno:", error);
                alert("Erro ao atualizar o aluno.");
            });
    } else {
        // Modo Criação: POST /alunos
        axios.post(API_URL, payload)
            .then(() => {
                hideModal();
                carregarAlunos();
            })
            .catch((error) => {
                console.error("Erro ao cadastrar aluno:", error);
                alert("Erro ao matricular o aluno.");
            });
    }
});

// Carregar alunos da API REST
function carregarAlunos() {
    axios.get(API_URL)
        .then((response) => {
            todosAlunos = response.data || [];
            renderizarAlunos(todosAlunos);
        })
        .catch((error) => {
            console.error("Erro ao carregar alunos:", error);
            alunoList.innerHTML = `<li class="list-group-item text-danger text-center py-4">Erro ao carregar os dados da API REST.</li>`;
        });
}

// Filtro de busca em tempo real
if (searchInput) {
    searchInput.addEventListener("input", () => {
        const termo = searchInput.value.toLowerCase().trim();
        const filtrados = todosAlunos.filter(aluno => 
            (aluno.nome && aluno.nome.toLowerCase().includes(termo)) ||
            (aluno.email && aluno.email.toLowerCase().includes(termo)) ||
            (aluno.curso && aluno.curso.toLowerCase().includes(termo))
        );
        renderizarAlunos(filtrados);
    });
}

// Obter iniciais do nome para o avatar
function getIniciais(nome) {
    if (!nome) return "AL";
    const partes = nome.trim().split(" ");
    if (partes.length === 1) return partes[0].substring(0, 2).toUpperCase();
    return (partes[0][0] + partes[partes.length - 1][0]).toUpperCase();
}

// Cores dos cursos
function getBadgeColor(curso) {
    if (!curso) return "badge-secondary";
    const c = curso.toLowerCase();
    if (c.includes("software")) return "badge-primary";
    if (c.includes("ciência") || c.includes("computação")) return "badge-success";
    if (c.includes("sistemas") || c.includes("informação")) return "badge-info";
    if (c.includes("dados")) return "badge-warning";
    return "badge-dark";
}

// Renderização na lista HTML
function renderizarAlunos(alunos) {
    totalAlunos.textContent = todosAlunos.length;
    alunoList.innerHTML = "";

    if (!alunos || alunos.length === 0) {
        const emptyItem = document.createElement("li");
        emptyItem.className = "list-group-item text-muted text-center py-5";
        emptyItem.innerHTML = `
            <i class="fas fa-user-graduate fa-2x mb-2 d-block text-secondary"></i>
            Nenhum aluno encontrado no banco de dados.
        `;
        alunoList.appendChild(emptyItem);
        return;
    }

    alunos.forEach((aluno) => {
        const listItem = document.createElement("li");
        listItem.className = "list-group-item aluno-item";

        const badgeClass = getBadgeColor(aluno.curso);
        const iniciais = getIniciais(aluno.nome);

        listItem.innerHTML = `
            <div class="d-flex align-items-center flex-grow-1 mr-3">
                <div class="aluno-avatar mr-3">
                    ${iniciais}
                </div>
                <div class="aluno-info">
                    <div class="d-flex align-items-center flex-wrap gap-2">
                        <span class="aluno-name font-weight-bold mr-2">${aluno.nome}</span>
                        <span class="badge ${badgeClass} mr-2">${aluno.curso}</span>
                    </div>
                    <div class="aluno-details text-muted small mt-1">
                        <span class="mr-3"><i class="fas fa-envelope mr-1"></i>${aluno.email}</span>
                        <span class="aluno-id" title="Identificador único (Chave Primária no H2)"><i class="fas fa-key mr-1"></i>ID: ${aluno.id}</span>
                    </div>
                </div>
            </div>
            <div class="actions d-flex">
                <button class="btn btn-sm btn-outline-primary mr-1 btn-edit" title="Editar dados">
                    <i class="fas fa-edit"></i>
                </button>
                <button class="btn btn-sm btn-outline-danger btn-delete" title="Excluir matrícula">
                    <i class="fas fa-trash-alt"></i>
                </button>
            </div>
        `;

        // Eventos dos botões
        listItem.querySelector(".btn-edit").addEventListener("click", () => showModalEditar(aluno));
        
        listItem.querySelector(".btn-delete").addEventListener("click", () => {
            if (confirm(`Tem certeza que deseja excluir o aluno "${aluno.nome}" do banco de dados?`)) {
                axios.delete(`${API_URL}/${aluno.id}`)
                    .then(() => carregarAlunos())
                    .catch((error) => {
                        console.error("Erro ao excluir aluno:", error);
                        alert("Erro ao excluir aluno do banco de dados.");
                    });
            }
        });

        alunoList.appendChild(listItem);
    });
}

document.addEventListener("DOMContentLoaded", carregarAlunos);
