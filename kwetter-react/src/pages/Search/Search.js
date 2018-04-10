import React, { Component } from 'react';
import './Search.css';
import Trends from '../../components/Trends/Trends';
import { connect } from 'react-redux';
import { searchKweets, setTrends } from '../../actions/index';
import Kweets from '../../components/Kweets/Kweets';

class Search extends Component {
    constructor(props) {
        super(props);
    }

    componentDidMount() {
        const { query } = this.props.match.params;

        if (query) {
            this.props.onSearchKweets(query);
        }

        this.props.onSetTrends();
    }

    render() {
        return (
            <div className="search">
                <div className="search__trends">
                    <Trends trends={this.props.trends}>
                        <h2>Trending</h2>
                    </Trends>
                </div>
                <div className="search__results">
                    <Kweets kweets={this.props.kweetsFound}/>
                </div>
            </div>
        );
    }
}

const mapStateToProps = ({ kweets }) => ({ ...kweets });

const mapDispatchToProps = (dispatch) => ({
    onSearchKweets: (query) => dispatch(searchKweets(query)),
    onSetTrends: () => dispatch(setTrends())
});

export default connect(mapStateToProps, mapDispatchToProps)(Search);