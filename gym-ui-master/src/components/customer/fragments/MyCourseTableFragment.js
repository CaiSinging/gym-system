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
import Link from "@material-ui/core/Link";
import RouterLink from "../../common/RouterLink";
import Button from "@material-ui/core/Button";
import Moment from "react-moment";
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
    {id: 'cname', align: 'left', label: '课程名称'},
    {id: 'teacher', align: 'left', label: '教练'},
    {id: 'start_time', align: 'left', label: '开课时间'},
    {id: 'end_time', align: 'left', label: '结课时间'},
    {id: 'time_desc', align: 'left', label: '上课时间描述'},
    {id: 'operations', align: 'center', label: '操作'},
];


class MyCourseTableFragment extends React.Component {

    constructor(props) {
        super(props);
        this.state = {courseSelections: [], page: 0, rowsPerPage: 5};
    }

    componentDidMount() {
        axiosInstance.get('/courseSelections/mySelections').then(res => {
            this.setState({courseSelections: res.data.courseSelections});
        });
    }

    render() {
        const {classes} = this.props;

        const cancelSelectCourseRequest = function (id) {
            axiosInstance.delete('/courseSelections/cancelSelection/' + id).then((res) => {
                alert(res.data.message);
                // eslint-disable-next-line no-restricted-globals
                location.reload();
            }).catch((error) => {
                alert(error.response.data.message);
            });
        };

        return (<div className={classes.root}>
            <Paper className={classes.breadcrumbPaper}>
                <Breadcrumbs aria-label="breadcrumb">
                    <Link color="inherit" component={RouterLink} to="/customer/">首页</Link>
                    <Typography color="textPrimary">预约课程</Typography>
                </Breadcrumbs>
            </Paper>
            <Paper className={classes.paper}>
                <Typography className={classes.title} variant="h6" id="tableTitle">
                    已选课程
                </Typography>
                <div className={classes.tableWrapper}>
                    <Table
                        className={classes.table}
                        aria-labelledby="tableTitle"
                        aria-label="enhanced table"
                    >
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
                                (this.state.page + 1) * this.state.rowsPerPage
                            ).map((row, index) => {
                                return (
                                    <TableRow hover tabIndex={-1} key={index}>
                                        {/*onClick={event => handleRowClick(event, row.name)}*/}
                                        <TableCell component="th" scope="row">
                                            {row.course.cname}
                                        </TableCell>
                                        <TableCell>{row.course.teacher.name}</TableCell>
                                        <TableCell>
                                            <Moment format="YYYY-MM-DD" unix>{row.course.cstart / 1000}</Moment>
                                        </TableCell>
                                        <TableCell>
                                            <Moment format="YYYY-MM-DD" unix>{row.course.cfinish / 1000}</Moment>
                                        </TableCell>
                                        <TableCell>{row.course.ctime}</TableCell>
                                        <TableCell align="center">
                                            <Button onClick={() => cancelSelectCourseRequest(row.cno)}>退选</Button>
                                        </TableCell>
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

MyCourseTableFragment.propTypes = {
    classes: PropTypes.object.isRequired,
};
export default withStyles(styles)(MyCourseTableFragment);