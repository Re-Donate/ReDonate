let $chatHistory;
let $button;
let $textarea;
let $chatHistoryList;
const cores = ["#003d52", "#ec1c24", "#f79534", "#fe6722", "#3f8678", "#147169"];

function init() {
    cacheDOM();
    bindEvents();
    carregarPerfil();
}

function bindEvents() {
    $button.on('click', addMessage.bind(this));
    $textarea.on('keyup', addMessageEnter.bind(this));
}

function cacheDOM() {
    $chatHistory = $('.chat-history');
    $button = $('#sendBtn');
    $textarea = $('#message-to-send');
    $chatHistoryList = $chatHistory.find('ul');
}

function render(objMessage) {
    var templateResponse = Handlebars.compile($("#message-response-template").html());

    $.ajax({
        method: "GET",
        dataType: "json",
        url: url + "/getuser/" + objMessage.de,
        async: false,
        success: function (data, status) {
            var contextResponse = {
                response: objMessage.texto,
                time: objMessage.createdAt,
                userName: getNomeResumido(data.nomeUsuario.split(' '))
            };

            $chatHistoryList.append(templateResponse(contextResponse));
            scrollToBottom();
        }
    });
}

function renderMyMessages(objMessage) {
    var template = Handlebars.compile($("#message-template").html());

    $.ajax({
        method: "GET",
        dataType: "json",
        url: url + "/getuser/" + objMessage.de,
        async: false,
        success: function (data, status) {
            var context = {
                messageOutput: objMessage.texto,
                time: objMessage.createdAt,
                toUserName: getNomeResumido(data.nomeUsuario.split(' '))
            };

            $chatHistoryList.append(template(context));
            scrollToBottom();
        }
    });
}

function renderChatHistory(chatHistory) {
    $chatHistoryList.html('');
    for(let message of chatHistory) {
        console.log(`\n\n ${message.texto} \n\n`);
        if(message.de == selectedUser){
            render(message);
        }else{
            renderMyMessages(message);
        }
    }
}

function sendMessage(message) {
    let userId = $('#userId').val();
    sendMsg(userId, message);
    scrollToBottom();
    if (message.trim() !== '') {
        var template = Handlebars.compile($("#message-template").html());
        var context = {
            messageOutput: message,
            time: getCurrentTime(),
            toUserName: selectedUser
        };

        $chatHistoryList.append(template(context));
        scrollToBottom();
        $textarea.val('');
    }
}

function carregarPerfil() {
    $('.userLogo').each(function(index, element) {
        let cor = Math.floor(Math.random() * 6);
        $(element).css({"background-color": cores[cor]});
    });
}

function scrollToBottom() {
    $chatHistory.scrollTop($chatHistory[0].scrollHeight);
}

function getCurrentTime() {
    tempo = new Date();
    return tempo.toLocaleTimeString().replace(/([\d]+:[\d]{2})(:[\d]{2})(.*)/, "$1$3") + " | " + tempo.getDate() + "/" + (tempo.getMonth() + 1);
}

function getNomeResumido(nomes) {
    return nomes[1] ? nomes[0] + ' ' + nomes[1] : nomes[0];
}

function addMessage() {
    sendMessage($textarea.val());
}

function addMessageEnter(event) {
    // enter was pressed
    if (event.keyCode === 13) {
        addMessage();
    }
}

init();

