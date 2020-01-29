const seminar = require('express')()
const buatseminarController = require('../controller/buatseminar')

seminar.post('/inputseminar', (req, res) => {
  buatseminarController.inputseminar(req.body)
  .then(result => {
    res.json(result)
  }).catch(err => {
    res.json(err)
  })
})

seminar.get('/getAllseminar', (req, res) => {
  buatseminarController.getAllseminar()
  .then(result => {
    res.json(result)
  }).catch(err => {
    res.json(err)
  })
})

seminar.get('/get/:id', (req, res) => {
  buatseminarController.getByID(req.params.id)
  .then(result => {
    res.json(result)
  }).catch(err => {
    res.json(err)
  })
})

seminar.put('/edit/:id', (req, res) => {
  buatseminarController.editseminar(req.params.id, req.body)
  .then(result => {
    res.json(result)
  }).catch(err =>{
    res.json(err)
  })
})

seminar.delete('/delete/:id', (req, res) =>{
  buatseminarController.deleteseminar(req.params.id)
  .then(result => res.json(result))
  .catch(error => res.json(error))
})

module.exports = seminar