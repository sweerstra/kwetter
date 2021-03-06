import React, { Component } from 'react';
import './EditProfileDetail.css';
import enhanceWithClickOutside from 'react-click-outside';

class EditProfileDetail extends Component {
    constructor(props) {
        super(props);

        this.state = { value: this.props.value, isEditing: false };
    }

    render() {
        const { value, isEditing } = this.state;
        const { children, className, name, placeholder } = this.props;

        const element = isEditing
            ? <input className="input"
                     value={value}
                     name={name}
                     placeholder={placeholder}
                     autoFocus="true"
                     onKeyDown={this.onKeyDown}
                     onChange={this.onChange}/>
            : children;

        return (
            <div className={`${className} edit-detail`}
                 onDoubleClick={this.startEditing}>
                {element}
            </div>
        );
    }

    startEditing = () => {
        if (this.state.isEditing) return;

        this.setState({ isEditing: true });
    };

    onKeyDown = ({ keyCode }) => {
        const { value } = this.state;
        const { name } = this.props;

        if (keyCode === 13) {
            if (value) {
                this.props.onEdit({ [name]: value });
            }
            this.setState({ value, isEditing: false });
        } else if (keyCode === 27) {
            this.setState({ isEditing: false });
        }
    };

    onChange = (e) => {
        this.setState({ value: e.target.value });
    };

    handleClickOutside() {
        this.setState({ isEditing: false });
    }
}

export default enhanceWithClickOutside(EditProfileDetail);