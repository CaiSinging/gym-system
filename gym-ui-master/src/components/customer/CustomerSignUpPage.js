import React from 'react';
import LockOutlinedIcon from '@material-ui/icons/LockOutlined';
import {
    Avatar,
    Button,
    Container,
    CssBaseline,
    FormControl,
    Grid,
    InputLabel,
    Link,
    MenuItem,
    Select,
    TextField,
    Typography,
    withStyles
} from '@material-ui/core';
import PropTypes from "prop-types";
import axiosInstance from "./axiosInstance";


const styles = (theme => ({
    '@global': {
        body: {
            backgroundColor: theme.palette.common.white,
        },
    },
    paper: {
        marginTop: theme.spacing(8),
        display: 'flex',
        flexDirection: 'column',
        alignItems: 'center',
    },
    avatar: {
        margin: theme.spacing(1),
        backgroundColor: theme.palette.secondary.main,
    },
    formInputBox: {
        width: '100%',
        margin: theme.spacing(1, 0)
    },
    submit: {
        margin: theme.spacing(3, 0, 2),
    },
}));

class SignUpPage extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            name: '',
            gender: 'Male',
            tel: '',
            email: '',
            password: '',
            repeatPassword: '',
            error: null,
            successMessage: null,
            labelWidth: 0,
        };
        this.genderLabel = React.createRef();
    }

    componentDidUpdate(prevProps, prevState, snapshot) {
        if (this.state.labelWidth !== this.genderLabel.width)
            this.setState({labelWidth: this.genderLabel.width});
    }

    render() {
        const {classes, history} = this.props;
        const self = this;
        const handleChange = name => event => {
            this.setState({
                [name]: event.target.value,
            });
        };
        const handleSubmit = function () {
            if (self.state.password !== self.state.repeatPassword) {
                alert("两次输入的密码不一致。");
            } else {
                axiosInstance.put("/guest/guestRegister", {
                    name: self.state.name,
                    gender: self.state.gender,
                    tel: self.state.tel,
                    email: self.state.email,
                    password: self.state.password,
                }).then((res) => {
                    alert(res.data.message);
                    history.push('/');
                }).catch((reason) => {
                    alert(reason.response.data.error || reason.response.data.message);
                });
            }
        };

        return (
            <Container component="main" maxWidth="xs">
                <CssBaseline/>
                <div className={classes.paper}>
                    <Avatar className={classes.avatar}>
                        <LockOutlinedIcon/>
                    </Avatar>
                    <Typography component="h1" variant="h5">
                        顾客注册
                    </Typography>
                    <FormControl className={classes.formInputBox}>
                        <TextField autoComplete="off" variant="outlined" required fullWidth label="姓名"
                                   value={this.state.name} onChange={handleChange('name')}/>
                    </FormControl>
                    <FormControl variant="outlined" className={classes.formInputBox}>
                        <InputLabel ref={this.genderLabel} id="demo-simple-select-outlined-label">
                            性别
                        </InputLabel>
                        <Select labelId="demo-simple-select-outlined-label" id="demo-simple-select-outlined"
                                value={this.state.gender} onChange={handleChange("gender")}
                                labelWidth={this.state.labelWidth}>
                            <MenuItem value="Male">男</MenuItem>
                            <MenuItem value="Female">女</MenuItem>
                        </Select>
                    </FormControl>
                    <FormControl className={classes.formInputBox}>
                        <TextField variant="outlined" required fullWidth label="手机号" autoComplete="off"
                                   value={this.state.tel} onChange={handleChange('tel')}/>
                    </FormControl>
                    <FormControl className={classes.formInputBox}>
                        <TextField variant="outlined" required fullWidth label="邮箱" autoComplete="off"
                                   value={this.state.email} onChange={handleChange('email')}/>
                    </FormControl>
                    <FormControl className={classes.formInputBox}>
                        <TextField variant="outlined" required fullWidth label="密码" type="password"
                                   autoComplete="off" value={this.state.password}
                                   onChange={handleChange('password')}/>
                    </FormControl>
                    <FormControl className={classes.formInputBox}>
                        <TextField variant="outlined" required fullWidth label="确认密码" type="password"
                                   autoComplete="off" value={this.state.repeatPassword}
                                   onChange={handleChange('repeatPassword')}/>
                    </FormControl>
                    <Button type="submit" fullWidth variant="contained" color="primary" className={classes.submit}
                            onClick={handleSubmit}>注册</Button>
                    <Grid container justify="flex-end">
                        <Grid item>
                            <Link href="/" variant="body2">
                                已有账户？去登录
                            </Link>
                        </Grid>
                    </Grid>
                </div>
            </Container>
        )
    }
}

SignUpPage.propTypes = {
    classes: PropTypes.object.isRequired,
};
export default withStyles(styles)(SignUpPage);