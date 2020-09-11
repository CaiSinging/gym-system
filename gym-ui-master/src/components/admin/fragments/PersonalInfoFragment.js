import Paper from "@material-ui/core/Paper";
import React from "react";
import {Box, Breadcrumbs, Button, Grid, Link, TextField, Typography, withStyles,} from "@material-ui/core";
import RouterLink from "../../common/RouterLink";
import PropTypes from "prop-types";
import clsx from "clsx";
import axiosInstance from "../axiosInstance";

const styles = (theme => ({
    root: {
        width: '100%',
        marginTop: theme.spacing(3),
    },
    breadcrumbPaper: {
        marginBottom: theme.spacing(2),
        padding: theme.spacing(2),
    },
    paper: {
        width: '100%',
        marginBottom: theme.spacing(2),
    },
    title: {
        padding: theme.spacing(2),
    },
    container: {
        display: 'flex',
        flexWrap: 'wrap',
    },
    formInput: {
        width: '100%',
        marginLeft: theme.spacing(2),
        marginRight: theme.spacing(2)
    },
    textField: {
        marginLeft: theme.spacing(1),
        marginRight: theme.spacing(1),
        marginTop: theme.spacing(1),
        marginBottom: theme.spacing(1),
        width: 200,
    },
    formFieldLabel: {
        textAlign: "right",
    },
    formParagraph: {
        lineHeight: "1.1875em",
        margin: "6px 0 7px",
    },
    padding2: {
        padding: theme.spacing(2)
    }
}));

class PersonalInfoFragment extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            editing: false,
            name: null,
            tel: null,
            mail: null,
            newName: null,
            newTel: null,
            newMail: null,
        };
    }

    componentDidMount() {
        axiosInstance.get('/admin/myInformation').then(res => {
            this.setState({
                name: res.data.name,
                tel: res.data.tel,
                mail: res.data.email,
            });
            this.setState({
                newName: this.state.name,
                newTel: this.state.tel,
                newMail: this.state.mail,
            });
        });
    }

    render() {
        const {classes} = this.props;
        const handleEditButton = () => {
            if (this.state.editing) {
                axiosInstance.post('/admin/updateInformation', {
                    name: this.state.newName,
                    tel: this.state.newTel,
                    email: this.state.newMail
                }).then((res)=>{
                    // eslint-disable-next-line no-restricted-globals
                   location.reload();
                }).catch((error)=>{
                    if (error.response)
                        alert(error.response.data.error || error.response.data.message);
                }).finally(() => this.setState({editing: false}));
            } else
                this.setState({editing: true});
        };

        return (
            <div>
                <Paper className={classes.breadcrumbPaper}>
                    <Breadcrumbs aria-label="breadcrumb">
                        <Link color="inherit" component={RouterLink} to="/admin/">首页</Link>
                        <Typography color="textPrimary">个人信息</Typography>
                    </Breadcrumbs>
                </Paper>
                <Paper className={classes.paper}>
                    <Typography className={classes.title} variant="h6" id="tableTitle">
                        个人信息
                    </Typography>
                    <Box className={classes.padding2}>
                        <Grid container spacing={3}>
                            <Grid item xs={3} md={2} lg={1}>
                                <Typography
                                    className={clsx(classes.formFieldLabel, classes.formParagraph)}>姓名</Typography>
                            </Grid>
                            <Grid item xs={9} md={10} lg={11}>{
                                this.state.editing ? (
                                    <TextField fullWidth={true} margin="none" value={this.state.newName}
                                               onChange={(event) => {
                                                   this.setState({newName: event.target.value})
                                               }}/>
                                ) : (<Typography className={classes.formParagraph}>{this.state.name}</Typography>)
                            }</Grid>
                        </Grid>
                        <Grid container spacing={3}>
                            <Grid item xs={3} md={2} lg={1}>
                                <Typography
                                    className={clsx(classes.formFieldLabel, classes.formParagraph)}>手机号</Typography>
                            </Grid>
                            <Grid item xs={9} md={10} lg={11}>{
                                this.state.editing ? (
                                    <TextField fullWidth={true} margin="none" value={this.state.newTel}
                                               onChange={(event) => {
                                                   this.setState({newTel: event.target.value})
                                               }}/>
                                ) : (<Typography className={classes.formParagraph}>{this.state.tel}</Typography>)
                            }</Grid>
                        </Grid>
                        <Grid container spacing={3}>
                            <Grid item xs={3} md={2} lg={1}>
                                <Typography
                                    className={clsx(classes.formFieldLabel, classes.formParagraph)}>邮箱</Typography>
                            </Grid>
                            <Grid item xs={9} md={10} lg={11}>{
                                this.state.editing ? (
                                    <TextField fullWidth={true} margin="none" value={this.state.newMail}
                                               onChange={(event) => {
                                                   this.setState({newMail: event.target.value})
                                               }}/>
                                ) : (<Typography className={classes.formParagraph}>{this.state.mail}</Typography>)
                            }</Grid>
                        </Grid>
                        <div className={classes.formInput}>
                            <Button variant="outlined" onClick={handleEditButton}>
                                {this.state.editing ? "保存" : "修改"}
                            </Button>
                        </div>
                    </Box>
                </Paper>
            </div>
        );
    }
}

PersonalInfoFragment.propTypes = {
    classes: PropTypes.object.isRequired,
};


export default withStyles(styles)(PersonalInfoFragment);