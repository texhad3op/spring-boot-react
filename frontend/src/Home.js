import React, { Component } from 'react';
import './App.css';
import AppNavbar from './AppNavbar';
import Reader from './Reader';
import Reader2 from './Reader2';
import ClickButton from './ClickButton';
import { Link } from 'react-router-dom';
import { Button, Container } from 'reactstrap';

class Home extends Component {
    render() {
        return (
            <div>
                <ClickButton/>
                <AppNavbar/>
                <Container fluid>
                    <Button color="link"><Link to="/clients">Clients</Link></Button>
                </Container>
            </div>
        );
    }
}

export default Home;