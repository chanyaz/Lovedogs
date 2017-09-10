var mongoose = require("mongoose");

// create instance of Schema
var mongoSchema = mongoose.Schema;

var UserSchema = new mongoose.Schema({
    name: String,
    address: String,
    phone: String,
    email: String,
    password: String,
    updated_at: {type: Date, default: Date.now},
});

module.exports = mongoose.model('User', UserSchema);
