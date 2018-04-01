import React, { Component } from 'react';
import './App.css';
import { Redirect, Route, Switch } from 'react-router-dom';
import Login from '../../pages/login/login';
import Profile from '../../pages/profile/profile';

class App extends Component {
    render() {
        return (
            <div className="App">
                <Switch>
                    <Route path="/login" component={Login}/>
                    <Route path="/profile/:username/:kweetsType" component={Profile}/>
                    <Redirect to="/login"/>
                </Switch>
            </div>
        );
    }
}

export default App;
