const express = require('express')()
const http = require('http').Server(express)
const io = require('socket.io')(http)

io.on('connection', socket => {
    console.log('device connected')
    socket.on('disconnect', () => {
        console.log('device disconnected')
    })

    $('#send').on('click', () => {
        socket.emit('command', $('#command').val())
    })
})

http.listen(3000, () => {
    console.log('listening on port 3000')
})