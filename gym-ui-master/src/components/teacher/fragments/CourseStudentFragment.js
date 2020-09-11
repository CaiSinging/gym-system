import Paper from "@material-ui/core/Paper";
import Table from "@material-ui/core/Table";
import TableBody from "@material-ui/core/TableBody";
import TableRow from "@material-ui/core/TableRow";
import TableCell from "@material-ui/core/TableCell";
import TablePagination from "@material-ui/core/TablePagination";
import React from "react";
import Typography from "@material-ui/core/Typography";
import {withStyles} from "@material-ui/core";
import TableHead from "@material-ui/core/TableHead";
import Breadcrumbs from "@material-ui/core/Breadcrumbs";
import RouterLink from "../../common/RouterLink";
import Link from "@material-ui/core/Link";
import PropTypes from "prop-types";
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
    table: {
        minWidth: 750,
    },
    tableWrapper: {
        overflowX: 'auto',
    },
}));

const headCells = [
    {id: 'gid', align: 'left', label: '编号'},
    {id: 'gname', align: 'left', label: '姓名'},
    {id: 'gender', align: 'left', label: '性别'},
];

class CourseStudentFragment extends React.Component {
    constructor(props) {
        super(props);
        this.state = {courseSelections: [], page: 0, rowsPerPage: 5};
    }

    componentDidMount() {
        const params = this.props.match.params;
        axiosInstance.get('/courseSelections/findByCourse/' + params.id).then(res => {
            this.setState({courseSelections: res.data.courseSelections});
        });
    }

    render() {
        const {classes} = this.props;

        return (<div className={classes.root}>
            <Paper className={classes.breadcrumbPaper}>
                <Breadcrumbs aria-label="breadcrumb">
                    <Link color="inherit" component={RouterLink} to="/teacher/">首页</Link>
                    <Link color="inherit" component={RouterLink} to="/teacher/myCourse">查看课程</Link>
                    <Typography color="textPrimary">学生名单</Typography>
                </Breadcrumbs>
            </Paper>
            <Paper className={classes.paper}>
                <Typography className={classes.title} variant="h6" id="tableTitle">
                    学生名单
                </Typography>
                <div className={classes.tableWrapper}>
                    <Table className={classes.table} aria-labelledby="tableTitle">
                        <TableHead>
                            <TableRow>
                                {headCells.map(headCell => (
                                    <TableCell
                                        key={headCell.id}
                                        align={headCell.align}
                                    >
                                        {headCell.label}
                                    </TableCell>
                                ))}
                            </TableRow>
                        </TableHead>
                        <TableBody>
                            {this.state.courseSelections.slice(
                                this.state.page * this.state.rowsPerPage,
                                (this.state.page + 1) * this.state.rowsPerPage)
                                .map((row, index) => {
                                    return (
                                        <TableRow hover tabIndex={-1} key={row.name}>
                                            <TableCell component="th" scope="row">
                                                {row.guest.id}
                                            </TableCell>
                                            <TableCell align="left">{row.guest.name}</TableCell>
                                            <TableCell align="left">{row.guest.gender}</TableCell>
                                        </TableRow>
                                    );
                                })}

                        </TableBody>
                    </Table>
                </div>
                <TablePagination component="div" page={this.state.page} count={this.state.courseSelections.length}
                                 rowsPerPage={this.state.rowsPerPage} rowsPerPageOptions={[5, 10, 25, 50]}
                                 onChangePage={(event, newPage) => this.setState({page: newPage})}
                                 onChangeRowsPerPage={(event) => {
                                     this.setState({rowsPerPage: parseInt(event.target.value), page: 0});
                                 }}/>
            </Paper>
        </div>)
    }
}

CourseStudentFragment.propTypes = {
    classes: PropTypes.object.isRequired,
};
export default withStyles(styles)(CourseStudentFragment);