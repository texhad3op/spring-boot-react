import React, { Component } from 'react';

class ClickButton extends React.Component {

    press = () => {
        console.log(this);
        alert("Hello React3!")
    }
    render() {
        return <button onClick={this.press}>Click</button>;
    }
}

export default ClickButton;
