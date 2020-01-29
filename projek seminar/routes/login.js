const login = require('express')()
const loginController = require('../controller/login')

login.post('/daftar',(req , res) => {
    console.log(req.body)
   loginController.insert(req.body)
    .then(result => {
        res.json(result)
    }).catch(err => {
        res.json(err)
    })
})

login.post('/login', (req, res) => {
    console.log(req.body)
    loginController.login(req.body)
    .then(result => {
        res.json(result)
    }).catch(err => {
        res.json(err)
    })
    })

    login.get('/getAll', (req, res) => {
        loginController.getAll()
        .then(result => {
            res.json(result)
        }).catch(err => {
            res.json(err)
        })
        })
    


module.exports = login