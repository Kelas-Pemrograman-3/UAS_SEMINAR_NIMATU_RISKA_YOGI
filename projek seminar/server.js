const server = require('express')()
const bodyParser = require('body-parser')
const mongoose = require('mongoose')
const cors = require('cors')
const port = 5000


const mongoURI = 'mongodb://localhost:27017/seminar'

server.use(cors())

mongoose.connect(mongoURI,  {
    useNewUrlParser: true,
    useCreateIndex: true,
    useUnifiedTopology: true
}).then(() => {
    console.log('connect to db succes')
}).catch(err => {
    console.log('Error : ' + err)
})
server.use(bodyParser.json({
    extended: true,
    limit: '50mb'
}))
    server.use(bodyParser.urlencoded({
        extended: true,
        limit: '50mb'
    }))

// list router

server.use('/login', require('./routes/login'))

server.use('/buatseminar', require('./routes/buatseminar'))

server.use('/pesertaseminar', require('./routes/pesertaseminar'))

server.listen(port,function() {
   console.log('server started on port' + port)
})