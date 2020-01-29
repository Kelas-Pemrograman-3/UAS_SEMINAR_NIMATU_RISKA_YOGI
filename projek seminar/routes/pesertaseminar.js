const peserta = require('express')()
const pesertaseminarController = require('../controller/pesertaseminar')

peserta.post('/insertpeserta', (req, res) => {
  pesertaseminarController.insertpeserta(req.body)
  .then(result => {
    res.json(result)
  }).catch(err => {
    res.json(err)
  })
})

peserta.get('/getAllpeserta', (req, res) => {
  pesertaseminarController.getAllpeserta()
  .then(result => {
    res.json(result)
  }).catch(err => {
    res.json(err)
  })
})

peserta.get('/get/:id', (req, res) => {
  pesertaseminarController.getByID(req.params.id)
  .then(result => {
    res.json(result)
  }).catch(err => {
    res.json(err)
  })
})

peserta.put('/edit/:id', (req, res) => {
  pesertaseminarController.editpeserta(req.params.id, req.body)
  .then(result => {
    res.json(result)
  }).catch(err =>{
    res.json(err)
  })
})

peserta.delete('/delete/:id', (req, res) =>{
  pesertaseminarController.deletepeserta(req.params.id)
  .then(result => res.json(result))
  .catch(error => res.json(error))
})

module.exports = peserta