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
    scrollToBottom();
    // responses
    var templateResponse = Handlebars.compile($("#message-response-template").html());
    var contextResponse = {
        response: objMessage.texto,
        time: objMessage.created_at,
        userName: objMessage.from
    };

    setTimeout(function () {
        $chatHistoryList.append(templateResponse(contextResponse));
        scrollToBottom();
    }.bind(this), 1500);
}

function renderMyMessages(objMessage) {
    var template = Handlebars.compile($("#message-template").html());
    var context = {
        messageOutput: objMessage.texto,
        time: objMessage.created_at,
        toUserName: objMessage.to
    };

    $chatHistoryList.append(template(context));
    scrollToBottom();
    $textarea.val('');
}

function renderChatHistory(chatHistory) {
    $chatHistoryList.html('');
    for(let message of chatHistory) {
        if(message.from == selectedUser){
            render(message);
        }else{
            renderMyMessages(message);
        }
    }
}

function sendMessage(message) {
    let username = $('#userName').val();
    console.log(username)
    sendMsg(username, message);
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
    return new Date().toLocaleTimeString().replace(/([\d]+:[\d]{2})(:[\d]{2})(.*)/, "$1$3");
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

