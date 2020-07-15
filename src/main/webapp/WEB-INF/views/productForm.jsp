<%--
  Created by IntelliJ IDEA.
  User: zb
  Date: 2020/7/15
  Time: 20:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <script>
        var totalFileLength, totalUploaded, fileCount, filesUploaded;
        //To log everything on console
        function debug(s) {
            var debug = document.getElementById('debug');
            if (debug) {
                debug.innerHTML = debug.innerHTML + '<br/>' + s;
            }
        }

        //Will be called when upload is complete
        function onUploadComplete(e) {
            totalUploaded += document.getElementById('files').files[fileUploaded].size;
            fileUploaded++;
            debug('complete' + fileUploaded + " of " + fileCount);
            debug('totalUploaded: ' + totalUploaded);
            if (fileUploaded < fileCount) {
                uploadNext();
            } else {
                var bar = document.getElementById('bar');
                bar.style.width = '100%';
                bar.innerHTML = '100% complete';
                alert('Finished uploading file(s)')
            }
        }

        //Will continueously updated the progress bar
        function onUploadProgress(e) {
            if (e.lengthComputable) {
                var percentComplete = parseInt((e.loaded + totalUploaded) * 100 / totalFileLength)
                var bar = document.getElementById('bar')
                bar.style.width = percentComplete + '%';
                bar.innerHTML = percentComplete + ' % complete';
            } else {
                debug('unable to compute')
            }
        }

        function onUploadFailed(e) {
            alert("Error uploading file")
        }

        //Pick the next file in queue and upload it to remote server
        function uploadNext() {
            var xhr = new XMLHttpRequest();
            var fd = new FormData();
            var file = document.getElementById('files').files[filesUploaded];
            fd.append("multipartFile", file);
            xhr.addEventListener("progress", onUploadProgress,false);
            xhr.addEventListener("load", onUploadComplete, false);
            xhr.addEventListener("error", onUploadFailed, false);
            xhr.open("POST", "save-product");
            debug('uploading' + file.name);
            xhr.send(fd)
        }

        //Let'sbegin the upload process
        function startUpload() {
            totalUploaded = filesUploaded = 0;
            uploadNext();
        }

        //Event listeners for button clicks
        window.onload = function () {
            document.getElementById('files').addEventListener("change", onf)
        }
    </script>
</head>
<body>

</body>
</html>
