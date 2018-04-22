import React, { Component } from 'react';
import './Search.css';
import Trends from '../../components/Trends/Trends';
import { connect } from 'react-redux';
import { searchAndSetKweets, searchAndSetKweetsByTrend, setTrends } from '../../actions/index';
import KweetsContainer from '../../containers/KweetsContainer';
import NavigationContainer from '../../containers/NavigationContainer';

class Search extends Component {
    constructor(props) {
        super(props);
    }

    componentDidMount() {
        const { searchType, query } = this.props.match.params;

        switch (searchType) {
            case 'kweet':
                this.props.onSearchKweetsByText(query);
                break;
            case 'trend':
                this.props.onSearchKweetsByTrend(query);
                break;
        }

        this.props.onSetTrends();
    }

    render() {
        return (
            <div className="search">
                <NavigationContainer
                    onSearchEnter={query => this.props.history.push(query)}
                />
                <div className="search__trends">
                    <Trends trends={this.props.trends}/>
                </div>
                <div className="search__results">
                    <KweetsContainer kweets={this.props.kweetsFound}/>
                </div>
            </div>
        );
    }
}

const mapStateToProps = ({ kweets }) => ({ ...kweets });

const mapDispatchToProps = (dispatch) => ({
    onSearchKweetsByText: (query) => dispatch(searchAndSetKweets(query)),
    onSearchKweetsByTrend: (trend) => dispatch(searchAndSetKweetsByTrend(trend)),
    onSetTrends: () => dispatch(setTrends())
});

export default connect(mapStateToProps, mapDispatchToProps)(Search);